package com.example.Biblio.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BookTest {
    private String ValidISBN = "9780385472579";
    private String ValidDashedISBN = "978-03-85472-579";
    private Book testBook = new Book(ValidISBN);

    @Test
    public void testsetMainSubjectGroup_2_SubjectGroup(){
        testBook.setMainSubjectGroup("2");
        SubjectGroup sg = SubjectGroup.Bibliographies;
        assertEquals(sg, testBook.MainSubjectGroup);
        testBook.setMainSubjectGroup("Geschichte Europas");
        sg = SubjectGroup.EuropeanHistory;
        assertEquals(sg, testBook.MainSubjectGroup);
    }

    @Test
    public void testsetSubSubjectGroup_2_SubjectGroup(){
        testBook.setSubSubjectGroup("2");
        SubjectGroup sg = SubjectGroup.Bibliographies;
        assertEquals(sg, testBook.SubSubjectGroup);
        testBook.setSubSubjectGroup("Geschichte Europas");
        sg = SubjectGroup.EuropeanHistory;
        assertEquals(sg, testBook.SubSubjectGroup);
    }

    @Test
    public void testgetISBNNumbers_ISBNWithDashes_ISBNOnlyNumbers(){
        testBook = new Book(ValidDashedISBN);        

        assertEquals(ValidISBN, testBook.getISBNNumbers());
    }
}
