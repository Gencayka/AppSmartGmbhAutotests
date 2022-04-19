package ru.Chayka.exceptions;

public class NoSuchProductOptionException extends RuntimeException {
    public NoSuchProductOptionException(String message) {
        super(message);
    }
}
