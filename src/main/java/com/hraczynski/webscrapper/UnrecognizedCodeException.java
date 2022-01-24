package com.hraczynski.webscrapper;

public class UnrecognizedCodeException extends RuntimeException {
    public UnrecognizedCodeException(String code) {
        super("Type: " + code + " is unrecognized");
    }
}
