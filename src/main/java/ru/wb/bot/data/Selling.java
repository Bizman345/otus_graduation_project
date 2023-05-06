package ru.wb.bot.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Selling {

    @JsonProperty(value = "brand_name")
    private String brandName;

    @JsonProperty(value = "supplier_id")
    private Long supplierId;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
