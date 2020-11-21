package com.sda.weather.exception;

public class BadRequestExeption extends RuntimeException {
    public BadRequestExeption(String message) {
        super("BAD REQUEST: " + message);
    }
}
