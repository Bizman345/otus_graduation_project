package ru.wb.bot.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetail {

    @JsonProperty(value = "state")
    private Long state;

    @JsonProperty(value = "data")
    private ProductDetailData data;

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public ProductDetailData getData() {
        return data;
    }

    public void setData(ProductDetailData data) {
        this.data = data;
    }
}
