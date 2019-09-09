package ru.dbolonkin.sampleproject;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleBooksOutput {
    private final String publisher;
    private final String publishedDate;
}
