package ru.wb.bot.utils;

import java.math.BigDecimal;

public class PriceUtils {

    public static BigDecimal parsePriceToBigDecimal(String x) {
        BigDecimal price = new BigDecimal(x);
        BigDecimal zero = BigDecimal.valueOf(0).setScale(2);

        switch (price.compareTo(zero)) {
            case 0:
                price = zero;
            default:
                price = price.divide(new BigDecimal("100")).setScale(2);
        }

        return price;
    }
}
