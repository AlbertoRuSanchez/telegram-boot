package com.craft.telegramboot.bot;

import com.craft.telegramboot.config.TelegramBotConfiguration;
import com.craft.telegramboot.domain.model.Translation;
import com.craft.telegramboot.domain.service.TranslatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramBotAdapter extends TelegramLongPollingBot {

    private final TelegramBotConfiguration telegramBotConfiguration;
    private final TranslatorService translatorService;

    @Override
    public void onUpdateReceived(Update update) {

        final String messageTextReceived = update.getMessage().getText();

        String userName = update.getMessage().getChat().getFirstName();
        log.info("Text received from chat " + userName + " -> " + messageTextReceived);

        final long chatId = update.getMessage().getChatId();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        Translation translation = translatorService.translate(messageTextReceived, "es");
        log.info("Text translated -> " + translation.getTranslatedText());

        try {
            message.setText(translation.getTranslatedText());
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfiguration.getUserName();
    }

    @Override
    public String getBotToken() {

        return telegramBotConfiguration.getToken();
    }
}
