package com.craft.telegramboot.domain.model;

import lombok.Getter;
import lombok.Setter;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

@Setter
@Getter

public class Translation {

    private String translatedText;

    private Translation(String translatedText) {
        this.translatedText = translatedText;
    }

    public static Translation create(String translatedText) {
        return new Translation(translatedText);
    }
}
