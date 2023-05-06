package ru.wb.bot.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetailDataProduct {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "salePriceU")
    private String salePriceU;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalePriceU() {
        return salePriceU;
    }

    public void setSalePriceU(String salePriceU) {
        this.salePriceU = salePriceU;
    }
}
