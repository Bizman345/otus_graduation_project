package ru.wb.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.wb.bot.webhook.TelegramWebhookImplementation;

/**
 * Контроллер бота
 */
@RestController
public class BotController {

    @Autowired
    private TelegramWebhookImplementation telegramBot;

    /**
     * Метод обработки вебхука
     *
     * @param update - сущность запроса от сервера телеграма
     * @return - сущность ответа
     */
    @PostMapping(value = "/")
    public BotApiMethod<?> processMessage(@RequestBody Update update) {

        return telegramBot.onWebhookUpdateReceived(update);
    }
}
