package com.craft.telegramboot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RapidApiConfiguration {
    @Value("${rapidapi.host:}")
    private String host;
    @Value("${rapidapi.token}")
    private String token;
    @Value("${rapidapi.url}")
    private String url;

}
