package com.escars;

import com.escars.bot.BotOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
public class AppConfig {
    private final BotOptions botOptions;

    public AppConfig(BotOptions botOptions) {
        this.botOptions = botOptions;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botOptions.getPath()).build();
    }
}
