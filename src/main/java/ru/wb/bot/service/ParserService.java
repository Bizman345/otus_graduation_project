package ru.wb.bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.wb.bot.data.Product;
import ru.wb.bot.data.ProductDetail;
import ru.wb.bot.exceptions.NotOKStatusException;

/**
 * Сервис обхода и парсинга WB
 */
@Service
public class ParserService {

    private static final String PRODUCT_URL = "https://wbx-content-v2.wbstatic.net/ru/%s.json";
    private static final String PRODUCT_DETAIL_URL = "https://card.wb.ru/cards/detail?locale=ru&lang=ru&curr=rub&nm=%s";

    @Autowired
    RestTemplate restTemplate;

    /**
     * Парсинг карточки товара
     *
     * @param id - id товара
     * @return - товар с WB
     * @throws NotOKStatusException - исключение, если статус ответа не OK
     */
    public Product parseProduct(Long id) throws NotOKStatusException {
        ResponseEntity<Product>  productResponse = restTemplate.getForEntity(String.format(PRODUCT_URL, id) , Product.class);

        if (productResponse.getStatusCode() == HttpStatus.OK) {
            Product product = productResponse.getBody();
            return product;
        } else {
            throw new NotOKStatusException("Http ответ не ОК при получении информации по товару");
        }
    }

    /**
     * Парсинг детальной информации товара
     *
     * @param id - id товара
     * @return - деталка по товару WB
     * @throws NotOKStatusException - исключение, если статус ответа не OK
     * @throws JsonProcessingException - исключение маппера при преобразовании ответа в объект
     */
    public ProductDetail parseProductDetail(Long id) throws NotOKStatusException, JsonProcessingException {
        ResponseEntity<String>  productDetailDataResponse = restTemplate.getForEntity(String.format(PRODUCT_DETAIL_URL, id) , String.class);
        if (productDetailDataResponse.getStatusCode() == HttpStatus.OK) {
            //Можно было бы использовать restTemplate.getForEntity со вторым параметром ProductDetail.class, но 1 раз из 10 приходит ответный content type [text/plain;charset=utf-8], на который спринг не находит конвертер.

            ObjectMapper mapper = new ObjectMapper();
            String productDetailString = productDetailDataResponse.getBody();
            ProductDetail productDetail = mapper.readValue(productDetailString, ProductDetail.class);

            return productDetail;
        } else {
            throw new NotOKStatusException("Http ответ не ОК при получении детальной информации по товару");
        }
    }

}
