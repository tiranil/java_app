package ru.dbolonkin.sampleproject.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;


@Entity(name = "author")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    @SequenceGenerator(name = "author_generator", sequenceName = "author_id_seq")
    @Column(name = "author_id", updatable = false, nullable = false)
    private Long authorId;
    private String author;
    private String phone;
    private String passport;
    private String gender;
    private String birthday;

    @OneToMany(mappedBy = "authorEntity", fetch = FetchType.LAZY, orphanRemoval=true)
    private Collection<BookEntity> books;
}
