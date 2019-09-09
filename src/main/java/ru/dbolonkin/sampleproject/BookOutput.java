package ru.dbolonkin.sampleproject;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class BookOutput {
    private final Long bookId;
    private final String name;
    private final Long authorId;
}
