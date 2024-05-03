package com.example.Biblio.Exception;

public class CantAddBookException extends RuntimeException{
    public CantAddBookException(String message){
        super(message);
    }
}
