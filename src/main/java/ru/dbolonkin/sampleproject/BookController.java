package ru.dbolonkin.sampleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dbolonkin.sampleproject.exception.BookEntityNotFoundException;
import ru.dbolonkin.sampleproject.persistence.BookEntity;
import ru.dbolonkin.sampleproject.persistence.BookRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<BookOutput> getBooks() {
        List<BookOutput> bookOutputList = new ArrayList<>();
        for ( BookEntity entity : bookRepository.findAll()) {
            bookOutputList.add(new BookOutput(entity.getBookId(), entity.getName(),entity.getAuthorEntity().getAuthorId()));

        }
        return bookOutputList;
    }





    @GetMapping("/{id}")
    public BookOutput getOne (@PathVariable long id) throws BookEntityNotFoundException {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new BookEntityNotFoundException(id));
        return new BookOutput(bookEntity.getBookId(),bookEntity.getName(),bookEntity.getAuthorEntity().getAuthorId());
    }

    @PostMapping()
    public BookEntity createBook(@Valid @RequestBody BookEntity book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public BookEntity updateNote(@PathVariable(value = "id") Long id,
                           @Valid @RequestBody BookEntity bookOutput) throws BookEntityNotFoundException {

        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookEntityNotFoundException(id));

        book.setName(bookOutput.getName());


        BookEntity updatedBook = bookRepository.save(book);

        return updatedBook;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long id) throws BookEntityNotFoundException {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookEntityNotFoundException(id));

        bookRepository.delete(book);

        return ResponseEntity.ok().build();
    }




    //TODO Добавить методы на все операции - (получение списка книг, получение конкретной по айди, удаление конкретной, редактирование, создание новой).
    // Get - чтение, Post - создание, Put - обновление, Delete - удаление.
    // Добавить имя, телефон, паспорт, пол и год рождения автора. Отдельная таблица, связанная с книгой - один ко многим. 1 автор - много книг. Репозиторий + сущность Jpa + таблица в БД.
    // Написать любой тест. (Пример на репозиторий автора).
    // Сделать ручки для работы с автором. Отдельный контролер.
    // Сделать фильтрацию книг по автору. Придется добавить метод в репозиторий.
}
