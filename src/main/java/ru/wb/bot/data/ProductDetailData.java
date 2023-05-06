package ru.wb.bot.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetailData {

    @JsonProperty(value = "products")
    private List<ProductDetailDataProduct> detailDataProducts;

    public List<ProductDetailDataProduct> getDetailDataProducts() {
        return detailDataProducts;
    }

    public void setDetailDataProducts(List<ProductDetailDataProduct> detailDataProducts) {
        this.detailDataProducts = detailDataProducts;
    }
}
