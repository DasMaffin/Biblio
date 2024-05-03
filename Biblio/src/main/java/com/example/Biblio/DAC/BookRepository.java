package com.example.Biblio.DAC;

import java.util.Optional;
// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.Biblio.Model.Book;

import jakarta.transaction.Transactional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BookRepository extends CrudRepository<Book, String> {
    @Transactional
    void deleteBookByISBN(String ISBN);

    Optional<Book> findBookByISBN(String ISBN);
}