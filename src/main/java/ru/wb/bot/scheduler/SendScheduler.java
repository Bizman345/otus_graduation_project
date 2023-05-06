package ru.wb.bot.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.wb.bot.entity.PriceEntity;
import ru.wb.bot.entity.ProductEntity;
import ru.wb.bot.entity.UserEntity;
import ru.wb.bot.repositories.PriceRepository;
import ru.wb.bot.repositories.ProductRepository;
import ru.wb.bot.webhook.TelegramWebhookImplementation;

import java.util.List;

/**
 * Шедулер отправки сообщений подписчикам
 */
@Component
public class SendScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(SendScheduler.class);
    private static final String MESSAGE = "Изменилась цена на товар \"%s\".\nНовая цена - %s\nПодробнее: \nhttps://www.wildberries.ru/catalog/%s/detail.aspx";
    private static final Integer LESS = -1;
    private static final Integer LAST_ITEM = 0;
    private static final Integer BEFORE_LAST_ITEM = 1;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private TelegramWebhookImplementation telegramBot;

    @Scheduled(fixedDelay = 100000)
    public void sendDiscountToUsers() {
        List<ProductEntity> productEntities= productRepository.findAll();

        for (ProductEntity product : productEntities) {
            List<PriceEntity> priceEntityList = priceRepository.getPriceEntityByProductIdOrderByDateDesc(product.getId());
            if (priceEntityList.size() > 1) {
                PriceEntity lastPrice = priceEntityList.get(LAST_ITEM);
                PriceEntity beforeLastPrice = priceEntityList.get(BEFORE_LAST_ITEM);

                if (!lastPrice.getSent() && lastPrice.getValue().compareTo(beforeLastPrice.getValue()) == LESS ) {
                    sendProductToUsers(product, lastPrice);
                }

                lastPrice.setSent(true);
                priceRepository.save(lastPrice);

            }
        }
    }

    private void sendProductToUsers(ProductEntity product, PriceEntity price) {
        List<UserEntity> subscribers = product.getSubscribers();
        for (UserEntity subscriber : subscribers) {
            SendMessage request = new SendMessage(subscriber.getChatId().toString(), String.format(MESSAGE, product.getSubjectName(), price.getValue(), product.getId()));
            request.enableWebPagePreview();

            try {
                telegramBot.execute(request);
            } catch (TelegramApiException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
