package com.craft.telegramboot.client;

import com.craft.telegramboot.domain.model.Translation;

import java.io.IOException;

public interface TranslatorRepository {

   Translation translate(String text, String language) throws IOException;

}
