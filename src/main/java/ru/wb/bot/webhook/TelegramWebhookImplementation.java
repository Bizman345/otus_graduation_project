package ru.wb.bot.webhook;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.wb.bot.config.PropertiesConfig;
import ru.wb.bot.entity.ProductEntity;
import ru.wb.bot.entity.UserEntity;
import ru.wb.bot.repositories.ProductRepository;
import ru.wb.bot.repositories.UserRepository;

import java.util.List;

/**
 * Имплементация механизма обработки вебхука
 */
@Component
public class TelegramWebhookImplementation extends TelegramWebhookBot {
    private static final String RIGHT_URL = "https://www.wildberries.ru/catalog/";
    private static final String SEPARATOR = "/";
    private static final String DELETE = "delete";
    private static final Integer ID_ITEM_NUMBER = 4;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public TelegramWebhookImplementation() {
        super();
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    /**
     * Метод получения токена бота
     *
     * @return  токен бота
     */
    @Override
    public String getBotToken() {
        return propertiesConfig.getToken();
    }

    /**
     * Метод обработки вебхука
     *
     * @param update - сущность запроса от сервера телеграма
     * @return - сущность ответа
     */
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            Long userId = update.getMessage().getChatId();
            UserEntity user = userRepository.getUserEntityByChatId(userId);
            if (user == null) {
                user = new UserEntity();
                user.setChatId(userId);
                user.setName(update.getMessage().getFrom().getUserName());

                userRepository.save(user);
            }

            String text = update.getMessage().getText();
            if (StringUtils.hasText(text)) {
                if (text.contains(RIGHT_URL)) {
                    String[] strings = text.split(SEPARATOR);
                    Long productId = Long.parseLong(strings[ID_ITEM_NUMBER]);

                    ProductEntity productEntity = productRepository.getProductEntityById(productId);

                    if (productEntity == null) {
                        if (text.contains(DELETE)) {
                            productEntity.getSubscribers().remove(user);
                        } else {
                            productEntity = new ProductEntity();
                            productEntity.setId(productId);
                            productEntity.setSubscribers(List.of(user));

                            productRepository.save(productEntity);
                        }
                    } else {
                        UserEntity finalUser = user;
                        List<UserEntity> subscribers = productEntity.getSubscribers();
                        if (subscribers.stream().filter(u -> u.getId().equals(finalUser.getId())).count() == 0) {
                            subscribers.add(user);

                            productRepository.save(productEntity);
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
