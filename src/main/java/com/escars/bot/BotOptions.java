package com.escars.bot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
@ConfigurationProperties("bot")
@Getter
@Setter
public class BotOptions extends DefaultBotOptions {
    private String username;
    private String token;
    private String path;
}
