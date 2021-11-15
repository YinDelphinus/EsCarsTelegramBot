package com.escars.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Component
public class Bot extends SpringWebhookBot {
    private final BotOptions botOptions;

    public Bot(BotOptions botOptions, SetWebhook setWebhook) {
        super(botOptions, setWebhook);
        this.botOptions = botOptions;
    }

    @Override
    public String getBotUsername() {
        return botOptions.getUsername();
    }

    @Override
    public String getBotToken() {
        return botOptions.getToken();
    }

    @Override
    public String getBotPath() {
        return botOptions.getPath();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message == null || !message.hasText()) {
            return null;
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("Hello world");
        return sendMessage;
    }

}
