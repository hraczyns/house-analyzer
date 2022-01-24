package com.hraczynski.webscrapper;

public class NumberUtils {

    public static Double parseNumber(String str, boolean defaultZero) {
        String escaped = str.replaceAll("[^0-9.,]", "").replace(",", ".");
        if (defaultZero && escaped.isEmpty()) {
            escaped = "-1";
        }
        try {
            return Double.parseDouble(escaped);
        } catch (RuntimeException e) {
            return -1.0;
        }
    }
}
