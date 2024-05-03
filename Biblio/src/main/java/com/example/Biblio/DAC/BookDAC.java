package com.example.Biblio.DAC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.sql.Date;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.Biblio.Model.Book;
import com.example.Biblio.Service.BookService;
import com.google.gson.*;

@Controller
@RequestMapping(path="/book")
public class BookDAC {
    //#region Private fields
    private static final String[] Prefix = new String[]
    {
        "978","979"
    };
    //#endregion

    private final BookService bookService;

    public BookDAC(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Book>> getAllBooks(){
        Iterable<Book> Books = bookService.findAllBooks();
        return new ResponseEntity<>(Books, HttpStatus.OK);
    }

    @GetMapping("/find/{isbn}")
    public ResponseEntity<Book> findBookByISBN(@PathVariable("isbn") String ISBN){
        Book Book = bookService.findBookByISBN(ISBN);
        return new ResponseEntity<>(Book, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book _book){
        _book = fillEmptyFields(_book);
        Book book = bookService.AddBook(_book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book _book){
        _book = fillEmptyFields(_book);
        Book book = bookService.updateBook(_book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable("isbn") String ISBN){
        bookService.deleteBook(ISBN); 
        return new ResponseEntity<>(HttpStatus.OK);
    }        

    //#region checkISBNValidity

    // 13 Digits
    // First 3 digits ALWAY 978 OR 979
    //
    // Last digit is checking digit. 
    // Step 1: Multiply every digit in an even space (2nd, 4th, 6th, 8th, 10th 12th) with 3, then add all of them together with the odd spaces.
    // Step 2: Find closest higher multiple of 10 - get difference to 1st calculated number
    // Difference = check number
    // Formula: abc-d-ef-ghijkl-m > Math.ceil(a + b*3 + c + d*3 + e + f*3 + g + h*3 + i + j*3 + k + l*3) - a + b*3 + c + d*3 + e + f*3 + g + h*3 + i + j*3 + k + l*3 = m
    public static Boolean CheckFirstThree(String s)
    {
        final String checkString = s.substring(0, 3);
        
        return Arrays.stream(Prefix).anyMatch(str -> str.equals(checkString));
    }

    public static Boolean CheckSum(String cs)
    {
        // Calculate checksum
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(cs.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checksum = 10 - (sum % 10);
        if (checksum == 10) {
            checksum = 0;
        }

        // Check if the calculated checksum matches the last digit of the ISBN
        int lastDigit = Character.getNumericValue(cs.charAt(12));
        return checksum == lastDigit;
    }

    //#endregion

    // Hard to test due to outside service dependency.
    public static String retrieveBookData(Book book){
        String responseString = "";
        if (book.ReleaseDate == null || book.Pages == 0 || book.Language == null
                || book.MainSubjectGroup == null || book.SubSubjectGroup == null) {
            try 
            {
                URI uri = new URI("https", "openlibrary.org", "/isbn/" + book.ISBN + ".json", null);                

                HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) 
                {
                    response.append(line);
                }
                reader.close();

                responseString = response.toString();

                conn.disconnect();
            } catch (URISyntaxException e) 
            {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return responseString;
    }

    public static Book fillEmptyFields(Book book){
        return fillEmptyFields(book, retrieveBookData(book));
    }

    public static String FindValueByKey(String json, String key){
        JsonElement je = findValueByKey(json, key);
        if(je != null){
            return je.toString()
            .replace("\"", "")
            .replace("[", "")
            .replace("]", "");
        }
        return "";
    }

    private static JsonElement findValueByKey(String json, String key) {
        System.out.println(json);
        System.out.println(key);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);

        String[] keys = key.split("/");

        // Navigate through the JSON structure using the keys
        for (String k : keys) {
            if (element instanceof JsonObject) {
                element = ((JsonObject) element).get(k);
            } else if (element instanceof JsonArray) {
                // Handle JsonArray type
                JsonArray jsonArray = (JsonArray) element;
                JsonArray arrayValues = new JsonArray();
                for (JsonElement e : jsonArray) {
                    if (e.isJsonObject()) {
                        JsonElement value = findValueByKey(e.toString(), k);
                        if (value != null) {
                            arrayValues.add(value);
                        }
                    }
                }
                return arrayValues.size() > 0 ? arrayValues : null;
            } else {
                // Handle non-JsonObject and non-JsonArray types (e.g., JsonPrimitive)
                return null;
            }
        }

        // Return the value as a JsonElement
        return element;
    }

    public static Book fillEmptyFields(Book book, String json) {
        // Fill the empty fields with the responseString's values
        if(book.Title == null || book.Title.isEmpty()){
            book.Title = BookDAC.FindValueByKey(json, "title");
        }
        if(book.AuthorKey == null || book.AuthorKey.isEmpty()){
            book.AuthorKey = BookDAC.FindValueByKey(json, "authors/key");
        }
        if(book.Publisher == null || book.Publisher.isEmpty()){
            book.Publisher = BookDAC.FindValueByKey(json, "publishers");
        }
        if(book.ReleaseDate == null){
            book.ReleaseDate = transformDate(BookDAC.FindValueByKey(json, "publish_date"));
        }
        if(book.Pages == 0){
            try {
                book.Pages = Integer.parseInt(BookDAC.FindValueByKey(json, "number_of_pages"));
            } catch (NumberFormatException e) {
                System.out.println("The string is not a valid integer.");
                book.Pages = 0;
            }
        }
        if(book.Language == null || book.Language.isEmpty()){
            String[] langParts = BookDAC.FindValueByKey(json, "languages/key").split("/");
            book.Language = LanguageDAC.ParseLanguageKey(langParts[langParts.length - 1]);
        }

        return book;
    }

    public static Date transformDate(String dateString) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(dateString, inputFormatter);
        } catch (Exception e) {
            // Handle parsing exception (e.g., if the input string is not in the expected format)
            e.printStackTrace();
        }

        if (localDate != null) {
            return java.sql.Date.valueOf(localDate);
        } else {
            // Default to January 1st of the year
            int year = Integer.parseInt(dateString);
            return java.sql.Date.valueOf(LocalDate.of(year, 1, 1));
        }
    }
}
