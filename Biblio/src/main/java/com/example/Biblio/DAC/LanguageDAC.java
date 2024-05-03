package com.example.Biblio.DAC;

import com.example.Biblio.Model.Language;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


class LanguageHelper {
    @SerializedName("Languages")
    private List<Language> languages;

    public List<Language> getLanguages() {
        return languages;
    }
}

public class LanguageDAC {
    private static String languageJson = "";

    public static String ParseLanguageKey(String key) {
        if (languageJson.equals("")) {
            try {
                // Read JSON file into a string
                String filePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\example\\Biblio\\Data\\languages.json";
                languageJson = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        // Parse JSON data into LanguageData object
        Gson gson = new Gson();
        LanguageHelper languageData = gson.fromJson(languageJson, LanguageHelper.class);

        // Get the list of languages from LanguageData object
        List<Language> languages = languageData.getLanguages();

        // Find language name by key
        for (Language language : languages) {
            if (language.getCode().equalsIgnoreCase(key)) {
                return language.getName();
            }
        }

        return null; // Language not found
    }
}