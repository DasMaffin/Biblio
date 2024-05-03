package com.example.Biblio.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Biblio.DAC.BookDAC;
import com.example.Biblio.DAC.BookRepository;
import com.example.Biblio.Exception.CantAddBookException;
import com.example.Biblio.Exception.UserNotFoundException;
import com.example.Biblio.Model.Book;

@Service
public class BookService {
    private final BookRepository bookRepo;

    @Autowired
    public BookService(BookRepository _bookRepo){
        this.bookRepo = _bookRepo;
    }

    public Book AddBook(Book book){
        if(book.getISBNNumbers().length() != 13 || 
        !BookDAC.CheckFirstThree(book.getISBNNumbers()) || 
        !BookDAC.CheckSum(book.getISBNNumbers()) ||
        CheckDuplicate(book))
        {
            throw new CantAddBookException("The book with the ISBN " + book.ISBN + " is already in the database, or the ISBN is incorrect.");
        }
        return bookRepo.save(book);
    }

    public Iterable<Book> findAllBooks(){
        return bookRepo.findAll();
    }

    public Book updateBook(Book book){
        return bookRepo.save(book);
    }

    public Book findBookByISBN(String ISBN){
        return bookRepo.findBookByISBN(ISBN).orElseThrow(() -> new UserNotFoundException("Book by ISBN " + ISBN + " was not found"));
    }

    public void deleteBook(String ISBN){
        bookRepo.deleteBookByISBN(ISBN);
    }

    // Returns true if a duplicate is found.
    public Boolean CheckDuplicate(Book book){
        Optional<Book> b = bookRepo.findBookByISBN(book.ISBN);
        if(b.isPresent()){
            return true;
        }
        return false;
    }
}
