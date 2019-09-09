package ru.dbolonkin.sampleproject.exception;

public class BookEntityNotFoundException extends Exception {
    private long bookId;
    public BookEntityNotFoundException(long bookId) {
        super(String.format("Book is not found with id : '%s'", bookId));
    }
}
