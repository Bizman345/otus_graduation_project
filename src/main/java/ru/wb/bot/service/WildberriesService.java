package ru.wb.bot.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wb.bot.data.Product;
import ru.wb.bot.data.ProductDetail;
import ru.wb.bot.data.ProductDetailDataProduct;
import ru.wb.bot.entity.PriceEntity;
import ru.wb.bot.entity.ProductEntity;
import ru.wb.bot.repositories.PriceRepository;
import ru.wb.bot.repositories.ProductRepository;

import java.util.Date;

import static ru.wb.bot.utils.PriceUtils.parsePriceToBigDecimal;

/**
 * Сервис парсинга и обработки с WB
 */
@Service
public class WildberriesService {
    private static final Logger LOG = LoggerFactory.getLogger(WildberriesService.class);


    @Autowired
    private ParserService parser;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;


    /**
     * Метод для парсинга и обработки(сохранения) информации по товару
     * @param id - id товара
     */
    public void handleProductInfo(Long id) {
        try {
            Product product = parser.parseProduct(id);
            ProductDetail productDetail = parser.parseProductDetail(id);

            ProductEntity productEntity = productRepository.getProductEntityById(id);

            ProductDetailDataProduct productDetailDataProduct = productDetail.getData().getDetailDataProducts().get(0);

            PriceEntity priceEntity = new PriceEntity();
            priceEntity.setProductId(id);
            priceEntity.setValue(parsePriceToBigDecimal(productDetailDataProduct.getSalePriceU()));
            priceEntity.setDate(new Date());
            priceEntity.setSent(false);

            if (productEntity.getBrandName() != null) {
                PriceEntity lastPrice = priceRepository.getFirstByProductIdOrderByDateDesc(id);

                if (lastPrice.getValue().compareTo(priceEntity.getValue()) != 0) {
                    priceRepository.save(priceEntity);
                }
            } else {
                productEntity.setBrandName(product.getSelling().getBrandName());
                productEntity.setSubjectName(product.getSubjName());

                productRepository.save(productEntity);
                priceRepository.save(priceEntity);
            }

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }
}
