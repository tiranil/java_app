package ru.dbolonkin.sampleproject.exception;

public class AuthorEntityNotFoundException extends Exception{
    private long id;
    public AuthorEntityNotFoundException(long authorId) {
        super(String.format("Author is not found with id : '%s'", authorId));
    }
}
