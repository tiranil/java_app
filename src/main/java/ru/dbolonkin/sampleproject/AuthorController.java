package ru.dbolonkin.sampleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dbolonkin.sampleproject.exception.AuthorEntityNotFoundException;
import ru.dbolonkin.sampleproject.persistence.AuthorRepository;
import ru.dbolonkin.sampleproject.persistence.AuthorEntity;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public List<AuthorOutput> getAuthors() {
        List<AuthorOutput> authorOutputList = new ArrayList<>();
        for ( AuthorEntity entity : authorRepository.findAll()) {
           authorOutputList.add(new AuthorOutput(entity.getAuthorId(), entity.getAuthor(),entity.getPhone(),entity.getPassport(),entity.getGender(),entity.getBirthday()));

        }
        return authorOutputList;
    }

    @GetMapping("/{id}")
    public AuthorOutput getOne (@PathVariable long id) throws AuthorEntityNotFoundException {
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorEntityNotFoundException(id));
        return new AuthorOutput(authorEntity.getAuthorId(),authorEntity.getAuthor(),authorEntity.getPhone(),authorEntity.getPassport(),authorEntity.getGender(),authorEntity.getBirthday());
    }

    @PostMapping()
    public AuthorEntity createAuthor(@Valid @RequestBody AuthorEntity author) {
        return authorRepository.save(author);
    }

    @PutMapping("/{id}")
    public AuthorEntity updateNote(@PathVariable(value = "id") Long authorId,
                                 @Valid @RequestBody AuthorEntity authorOutput) throws AuthorEntityNotFoundException {

        AuthorEntity author = authorRepository.findById( authorId)
                .orElseThrow(() -> new AuthorEntityNotFoundException( authorId));

        author.setAuthor(authorOutput.getAuthor());
        author.setPhone(authorOutput.getPhone());
        author.setPassport(authorOutput.getPassport());
        author.setGender(authorOutput.getGender());
        author.setBirthday(authorOutput.getBirthday());


        AuthorEntity updatedAuthor = authorRepository.save(author);

        return updatedAuthor;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable(value = "id") Long  authorId) throws AuthorEntityNotFoundException {
        AuthorEntity author = authorRepository.findById( authorId)
                .orElseThrow(() -> new AuthorEntityNotFoundException( authorId));

        authorRepository.delete(author);

        return ResponseEntity.ok().build();
    }



}
