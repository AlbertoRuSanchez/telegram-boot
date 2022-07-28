package com.craft.telegramboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class TelegramBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBootApplication.class, args);
    }

}
