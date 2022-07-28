package com.craft.telegramboot.bot;

import com.craft.telegramboot.config.TelegramBotConfiguration;
import com.craft.telegramboot.domain.model.Translation;
import com.craft.telegramboot.domain.service.TranslatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.MockitoAnnotations.openMocks;

public class TelegramBotAdapterTest {

    @Mock
    private TelegramBotConfiguration mockedTelegramBotConfiguration;
    @Mock
    private TelegramLongPollingBot mockedTelegramLongPollingBot;
    @Mock
    private TranslatorService mockedTranslatorService;

    private TelegramBotAdapter underTest;

    @BeforeEach
    void setUp() {
        openMocks(this);
        underTest = new TelegramBotAdapter(mockedTelegramBotConfiguration, mockedTranslatorService);
    }


    @Test
    void itShouldReturnTranslatedTextToTelegramBot(){
        //Given
        Update update = mockUpdateObject();
        given(mockedTranslatorService.translate(anyString(), anyString())).willReturn(Translation.create("translated text"));

        //When
        underTest.onUpdateReceived(update);

        //Then
        then(mockedTranslatorService).should().translate(update.getMessage().getText(), "es");

    }

    @Test
    void getBotUserNameShouldReturnTelegramBotUserName() {
        //Given
        String botName = "botName";
        given(mockedTelegramBotConfiguration.getUserName()).willReturn(botName);

        //When
        String botUserName = underTest.getBotUsername();

        //Then
        assertThat(botUserName).isNotNull();
        assertThat(botUserName).isEqualTo(botName);
    }

    @Test
    void getBotTokenShouldReturnTelegramBotToken() {
        //Given
        String token = "tokengivenbytbotfather";
        given(mockedTelegramBotConfiguration.getToken()).willReturn(token);

        //When
        String botUserName = underTest.getBotToken();

        //Then
        assertThat(botUserName).isNotNull();
        assertThat(botUserName).isEqualTo(token);
    }

    private Update mockUpdateObject() {
        Update update = new Update();
        Message message = new Message();
        message.setText("Hello");

        Chat chat = new Chat();
        chat.setId(1L);

        message.setChat(chat);
        update.setMessage(message);

        return update;
    }

}
