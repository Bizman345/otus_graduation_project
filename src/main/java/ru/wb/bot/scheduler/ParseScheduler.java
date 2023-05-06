package ru.wb.bot.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.wb.bot.entity.ProductEntity;
import ru.wb.bot.repositories.ProductRepository;
import ru.wb.bot.service.WildberriesService;

import java.util.List;

/**
 * Шедулер обхода и парсинга WB
 */
@Component
public class ParseScheduler {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WildberriesService wildberriesService;

    @Scheduled(fixedDelay = 60000)
    public void sendDiscountToUsers() {
        List<ProductEntity> productEntities = productRepository.findAll();

        for (ProductEntity product : productEntities) {
            wildberriesService.handleProductInfo(product.getId());
        }
    }
}
