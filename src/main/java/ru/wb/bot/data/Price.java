package ru.wb.bot.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Price {
    @JsonProperty(value = "RUB")
    private String rub;

    public String getRub() {
        return rub;
    }

    public void setRub(String rub) {
        this.rub = rub;
    }
}
