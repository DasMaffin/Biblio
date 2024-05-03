package com.example.Biblio.DAC;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Biblio.Model.Book;

@SpringBootTest
@AutoConfigureMockMvc
public class BookDACTest 
{
    private String ValidISBN = "9780385472579";
    private String InvalidISBN = "9733161484301";
    private Book testBook = new Book(ValidISBN);

    // Naming convention used: testMethod_Input_ExpectedOutput || testMethod_ExpectedOutput
    @Test
    public void testCheckSum_ValidISBN_True(){
        assertTrue(BookDAC.CheckSum(ValidISBN), "Expected checkSum to return true for a valid ISBN");
    }

    @Test
    public void testCheckSum_InvalidISBN_False(){
        assertFalse(BookDAC.CheckSum(InvalidISBN), "Expected checkSum to return false for an invalid ISBN");
    }
    
    @Test
    public void testcheckFirstThree_ValidISBN_True(){
        assertTrue(BookDAC.CheckFirstThree(ValidISBN), "Expected to return true for a valid ISBN");
    }
    
    @Test
    public void testcheckFirstThree_InvalidISBN_False(){
        assertFalse(BookDAC.CheckFirstThree(InvalidISBN), "Expected to return false for an invalid ISBN");
    }

    @Test
    public void testfillEmptyFields_BookEmpty_BookFull(){
        testBook = BookDAC.fillEmptyFields(testBook);

        assertEquals("Zen speaks", testBook.Title);
        assertEquals(159, testBook.Pages);
        assertEquals("Anchor Books", testBook.Publisher);
        assertEquals("/authors/OL223368A", testBook.AuthorKey);
        assertEquals("English", testBook.Language);
        assertEquals("1994-01-01", testBook.ReleaseDate.toString());
    }

    @Test
    public void testFindValueByKey_JSON_String(){
        // Zen Speaks, 159 pages, published by Anchor, authorkey "/authors/OL2677946A", language "/languages/eng"
        String json = "{\"publishers\": [\"Anchor Books\"], \"number_of_pages\": 159, \"subtitle\": \"shouts of nothingness\", \"contributors\": [{\"role\": \"Translator\", \"name\": \"Chan shuo\"}, {\"role\": \"Illustrator\", \"name\": \"Tsai Chih Chung\"}, {\"role\": \"Adaptation\", \"name\": \"Tsai Chih Chung\"}, {\"role\": \"Translator\", \"name\": \"Brian Bruya\"}], \"isbn_10\": [\"0385472579\"], \"pagination\": \"159 p. :\", \"covers\": [240726], \"lc_classifications\": [\"BQ9265.6 .T7313 1994\"], \"url\": [\"http://www.loc.gov/catdir/description/random046/93005405.html\"], \"key\": \"/books/OL1397864M\", \"authors\": [{\"key\": \"/authors/OL223368A\"}], \"publish_places\": [\"New York\"], \"contributions\": [\"Bruya, Brian, 1966-\"], \"subjects\": [\"Zen Buddhism -- Caricatures and cartoons.\"], \"uri_descriptions\": [\"Publisher description\"], \"genres\": [\"Caricatures and cartoons.\"], \"classifications\": {}, \"source_records\": [\"marc:marc_openlibraries_sanfranciscopubliclibrary/sfpl_chq_2018_12_24_run02.mrc:125374412:1424\", \"amazon:0385472579\", \"marc:marc_loc_2016/BooksAll.2016.part22.utf8:152602043:889\", \"ia:zenspeaksshoutso0000caiz\", \"bwb:9780385472579\", \"promise:bwb_daily_pallets_2022-06-13\", \"marc:marc_columbia/Columbia-extract-20221130-009.mrc:180445831:1552\"], \"title\": \"Zen speaks\", \"dewey_decimal_class\": [\"294.3/927\"], \"identifiers\": {\"librarything\": [\"192819\"], \"goodreads\": [\"979250\"]}, \"languages\": [{\"key\": \"/languages/eng\"}], \"lccn\": [\"93005405\"], \"local_id\": [\"urn:sfpl:31223062535233\", \"urn:bwbsku:O8-ACV-874\"], \"publish_date\": \"April 15, 1994\", \"publish_country\": \"nyu\", \"work_title\": [\"Chan shuo.\"], \"works\": [{\"key\": \"/works/OL1866073W\"}], \"type\": {\"key\": \"/type/edition\"}, \"uris\": [\"http://www.loc.gov/catdir/description/random046/93005405.html\"], \"ocaid\": \"zenspeaksshoutso0000caiz\", \"oclc_numbers\": [\"28419896\"], \"latest_revision\": 14, \"revision\": 14, \"created\": {\"type\": \"/type/datetime\", \"value\": \"2008-04-01T03:28:50.625462\"}, \"last_modified\": {\"type\": \"/type/datetime\", \"value\": \"2023-01-07T09:08:00.094884\"}}";
       
        assertEquals("Zen speaks", BookDAC.FindValueByKey(json, "title"));
        assertEquals("159", BookDAC.FindValueByKey(json, "number_of_pages"));
        assertEquals("Anchor Books", BookDAC.FindValueByKey(json, "publishers"));
        assertEquals("/authors/OL223368A", BookDAC.FindValueByKey(json, "authors/key"));
        assertEquals("/languages/eng", BookDAC.FindValueByKey(json, "languages/key"));
        assertEquals("April 15, 1994", BookDAC.FindValueByKey(json, "publish_date"));
        assertEquals("", BookDAC.FindValueByKey(json, "aaaaa"));
    }

    @Test
    public void testtransformDate_DateString_Date(){
        Date d = BookDAC.transformDate("April 15, 1994");
        assertEquals("1994-04-15", d.toString());

        d = BookDAC.transformDate("1994");        
        assertEquals("1994-01-01", d.toString());
    }

    // Testing database requests would require a different setup, like a container for the tests. 
    // Like this the tests use the live DB, making them very inaccurate 
    // (e.g. getting a list of books and checking the size, or searching a book by ISBN but it has been deleted, etc.)
}
