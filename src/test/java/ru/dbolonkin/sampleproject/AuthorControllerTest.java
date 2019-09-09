package ru.dbolonkin.sampleproject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.dbolonkin.sampleproject.exception.AuthorEntityNotFoundException;
import ru.dbolonkin.sampleproject.persistence.AuthorEntity;
import ru.dbolonkin.sampleproject.persistence.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


public class AuthorControllerTest {

    @Mock private AuthorRepository authorRepository;

    @Before public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        List<AuthorEntity> testAuthors = new ArrayList<>();
        testAuthors.add(createTestEntity());
        when(authorRepository.findAll()).thenReturn(testAuthors);
        AuthorController authorController = new AuthorController(authorRepository);
        List<AuthorOutput> authors = authorController.getAuthors();
        Mockito.verify(authorRepository).findAll();
        Assert.assertEquals(1, authors.size());
    }

    @Test
    public void test() throws AuthorEntityNotFoundException {
        AuthorController authorController = new AuthorController(authorRepository);
        when(authorRepository.findById(1L)).thenReturn(Optional.ofNullable(createTestEntity()));
        AuthorOutput actual = authorController.getOne(1L);
        Assert.assertEquals("Alexandr Belyanin", actual.getAuthor());
        Mockito.verify(authorRepository).findById(1L);
    }

    private AuthorEntity createTestEntity() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setAuthorId(1L);
        authorEntity.setAuthor("Alexandr Belyanin");
        authorEntity.setPhone("231231231");
        authorEntity.setPassport("3333 333333");
        authorEntity.setGender("Male");
        authorEntity.setBirthday("1993-24-12");
        return authorEntity;
    }

}

//Посмотреть SpringBoot тесты