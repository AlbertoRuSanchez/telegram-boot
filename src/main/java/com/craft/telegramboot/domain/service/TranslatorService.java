package com.craft.telegramboot.domain.service;


import com.craft.telegramboot.client.google.GoogleTranslatorRepositoryImpl;
import com.craft.telegramboot.client.TranslatorRepository;
import com.craft.telegramboot.domain.model.Translation;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TranslatorService {

    private final TranslatorRepository translatorRepository;

    public TranslatorService(GoogleTranslatorRepositoryImpl translatorRepository) {
        this.translatorRepository = translatorRepository;
    }

    public Translation translate(String text, String language) {
        try {
            return translatorRepository.translate(text, language);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
