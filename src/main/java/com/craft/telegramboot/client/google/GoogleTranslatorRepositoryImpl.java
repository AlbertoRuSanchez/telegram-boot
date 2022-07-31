package com.craft.telegramboot.client.google;

import com.craft.telegramboot.client.TranslatorRepository;
import com.craft.telegramboot.client.google.dto.GoogleTranslatorRs;
import com.craft.telegramboot.config.RapidApiConfiguration;
import com.craft.telegramboot.domain.model.Translation;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class GoogleTranslatorRepositoryImpl implements TranslatorRepository {

    private final ObjectMapper objectMapper;
    private final RapidApiConfiguration rapidApiConfiguration;
    private final OkHttpClient okHttpClient;

    public GoogleTranslatorRepositoryImpl(ObjectMapper objectMapper, RapidApiConfiguration rapidApiConfiguration, OkHttpClient okHttpClient) {
        this.objectMapper = objectMapper;
        this.rapidApiConfiguration = rapidApiConfiguration;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public Translation translate(String text, String language) throws IOException {

        RequestBody body = new FormBody.Builder()
                .add("source", "en")
                .add("target", language)
                .add("q", text)
                .build();

        Request request = new Request.Builder()
                .url(rapidApiConfiguration.getUrl())
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Accept-Encoding", "application/gzip")
                .addHeader("X-RapidAPI-Key", rapidApiConfiguration.getToken())
                .addHeader("X-RapidAPI-Host", rapidApiConfiguration.getHost())
                .build();

        Response response = okHttpClient.newCall(request).execute();

        if (Objects.isNull(response)) {
            throw new RuntimeException("Response from Google translator is null");
        }

        String translatedText = getJsonObjectValue(response);
        return Translation.create(translatedText);
    }

    private String getJsonObjectValue(Response response) {
        try {
            GoogleTranslatorRs googleTranslatorRs = objectMapper.readValue(response.body().string(), GoogleTranslatorRs.class);
            return googleTranslatorRs.getData().getTranslations().get(0).getTranslatedText();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON from Google Translate Response");
        }
    }

}
