package ru.wb.bot.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Product {
    private List<Long> colors;

    private String contents;

    @JsonProperty(value = "subj_name")
    private String subjName;

    @JsonProperty(value = "selling")
    private Selling selling;

    public List<Long> getColors() {
        return colors;
    }

    public void setColors(List<Long> colors) {
        this.colors = colors;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getSubjName() {
        return subjName;
    }

    public Selling getSelling() {
        return selling;
    }
}
