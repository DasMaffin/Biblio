package com.example.Biblio.Model;

import com.google.gson.annotations.SerializedName;

public class Language {
    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

