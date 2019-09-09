package ru.dbolonkin.sampleproject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorOutput {
    private final Long authorId;
    private final String author;
    private final String phone;
    private final String passport;
    private final String gender;
    private final String birthday;
}
