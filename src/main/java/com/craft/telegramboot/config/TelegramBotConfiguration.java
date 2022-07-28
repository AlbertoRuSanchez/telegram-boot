package com.craft.telegramboot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TelegramBotConfiguration {
    @Value("${bot.username:}")
    private String userName;
    @Value("${bot.apiKey}")
    private String token;

}
