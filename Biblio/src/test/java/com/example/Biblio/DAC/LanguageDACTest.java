package com.example.Biblio.DAC;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LanguageDACTest {
    @Test
    public void testParseLanguageKey(){
        assertEquals("English", LanguageDAC.ParseLanguageKey("eng"));
        assertEquals("Tatar", LanguageDAC.ParseLanguageKey("tat"));
        assertEquals("Sotho, Southern", LanguageDAC.ParseLanguageKey("sot"));
        assertEquals("Pali", LanguageDAC.ParseLanguageKey("pli"));
        assertEquals("Deutsch", LanguageDAC.ParseLanguageKey("ger"));
    }
}