package ru.dbolonkin.sampleproject.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator", sequenceName = "book_id_seq")
    @Column(name = "book_id", updatable = false, nullable = false)
    private Long bookId;
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId")
    private AuthorEntity authorEntity;
}
