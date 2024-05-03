package com.example.Biblio.Model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
public class Book  implements Serializable
{
    //#region Fields
    @Id
    @Column(nullable = false)
    public String ISBN;
    @Column(nullable = false)
    public String Title;   
    public String AuthorKey;
    public String Publisher;
    public Date ReleaseDate;
    public int Pages;
    public String Language;
    public SubjectGroup MainSubjectGroup;
    public SubjectGroup SubSubjectGroup;
    //#endregion

    @JsonSetter("MainSubjectGroup")
    public void setMainSubjectGroup(String mainSubjectGroup) {
        if (mainSubjectGroup == null || mainSubjectGroup.isEmpty()) {
            this.MainSubjectGroup = null;
        } else {
            Integer parsedValue = null;
            try{
                parsedValue = Integer.parseInt(mainSubjectGroup);
            }catch(NumberFormatException e){

            }
            if(parsedValue != null){
                this.MainSubjectGroup = SubjectGroup.values()[parsedValue];
            }
            else{
                this.MainSubjectGroup = SubjectGroup.fromString(mainSubjectGroup);
            }
        }
    }

    // Custom setter method for SubSubjectGroup to handle null values
    @JsonSetter("SubSubjectGroup")
    public void setSubSubjectGroup(String subSubjectGroup) {
        if (subSubjectGroup == null || subSubjectGroup.isEmpty()) {
            this.SubSubjectGroup = null;
        } else {
            Integer parsedValue = null;
            try{
                parsedValue = Integer.parseInt(subSubjectGroup);
            }catch(NumberFormatException e){

            }
            if(parsedValue != null){
                this.SubSubjectGroup = SubjectGroup.values()[parsedValue];
            }
            else{
                this.SubSubjectGroup = SubjectGroup.fromString(subSubjectGroup);
            }
        }
    }


    public Book(String _isbn){
        ISBN = _isbn;
    }

    public Book(){}

    public String getISBNNumbers(){
        return ISBN.replace("-", "");
    }
}
