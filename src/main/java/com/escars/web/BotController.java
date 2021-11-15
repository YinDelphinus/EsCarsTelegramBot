package com.escars.web;

import com.escars.bot.Bot;
import com.escars.model.SendMessageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
public class BotController {

    private final Bot bot;

    public BotController(Bot bot) {
        this.bot = bot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return bot.onWebhookUpdateReceived(update);
    }

    @PostMapping("/send")
    public List<String> sendMassage(@RequestBody SendMessageRequest message) throws TelegramApiException {
        if (message.getChatIds() == null) {
            //set all available?
            throw new NullPointerException("chatIds must not be empty!");
        }
        final List<String> successfulChatIds = message.getChatIds();

        SendMessage.SendMessageBuilder sendMessageBuilder = SendMessage.builder()
                .text(message.getHtmlText())
                //https://core.telegram.org/bots/api#html-style
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(Boolean.TRUE);

        List<CompletableFuture<Message>> sendingList = new ArrayList<>(successfulChatIds.size());
        for (String successfulChatId : successfulChatIds) {
            sendingList.add(bot.executeAsync(sendMessageBuilder.chatId(successfulChatId).build()));
        }
        for (int i = 0; i < message.getChatIds().size(); i++) {
            try {
                sendingList.get(i).get(1L, TimeUnit.MINUTES);
            } catch (Exception e) {
                successfulChatIds.remove(i);
            }
        }
        return successfulChatIds;
    }
}
