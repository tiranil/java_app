package ru.dbolonkin.sampleproject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.dbolonkin.sampleproject.persistence.AuthorEntity;
import ru.dbolonkin.sampleproject.persistence.AuthorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthorControllerIntegrationTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorRepository mockRepository;

    @Before
    public void init() {
        AuthorEntity authorEntity = new AuthorEntity(1L,"Alexandr Belyanin","33-00-66","3336 555333","male","1992-02-02");
        when(mockRepository.findById(1L)).thenReturn(Optional.of(authorEntity));
    }

    @Test
    public void find_authorId_OK() throws Exception {

        mockMvc.perform(get("/authors/1"))
                /*.andDo(print())*/
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId", is(1)))
                .andExpect(jsonPath("$.author", is("Alexandr Belyanin")))
                .andExpect(jsonPath("$.phone", is("33-00-66")))
                .andExpect(jsonPath("$.passport", is("3336 555333")))
                .andExpect(jsonPath("$.gender", is("male")))
                .andExpect(jsonPath("$.birthday", is("1992-02-02")));

        verify(mockRepository, times(1)).findById(1L);

    }

    @Test
    public void find_allAuthors_OK() throws Exception {

        List<AuthorEntity> authorEntities = Arrays.asList(
                new AuthorEntity(1L, "Alexandr Belyanin", "33-00-66", "3336 555333","male","1992-02-02" ),
                new AuthorEntity(2L, "Maks Frai", "32-00-55","5666 555555", "male", "1993-12-12"));

        when(mockRepository.findAll()).thenReturn(authorEntities);

        mockMvc.perform(get("/authors"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].authorId", is(1)))
                .andExpect(jsonPath("$[0].author", is("Alexandr Belyanin")))
                .andExpect(jsonPath("$[1].authorId", is(2)))
                .andExpect(jsonPath("$[1].author", is("Maks Frai")));

        verify(mockRepository, times(1)).findAll();
    }


    @Test
    public void save_author_OK() throws Exception {

        AuthorEntity newAuthorEntity = new AuthorEntity(1L,"Alexandr Belyanin","33-00-66","3336 555333","male","1992-02-02");
        when(mockRepository.save(any(AuthorEntity.class))).thenReturn(newAuthorEntity);

        mockMvc.perform(post("/authors")
                .content(om.writeValueAsString(newAuthorEntity))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId", is(1)))
                .andExpect(jsonPath("$.author", is("Alexandr Belyanin")))
                .andExpect(jsonPath("$.phone", is("33-00-66")))
                .andExpect(jsonPath("$.passport", is("3336 555333")))
                .andExpect(jsonPath("$.gender", is("male")))
                .andExpect(jsonPath("$.birthday", is("1992-02-02")));

        verify(mockRepository, times(1)).save(any(AuthorEntity.class));

    }

    @Test
    public void update_author_OK() throws Exception {

        AuthorEntity updatedAuthorEntity = new AuthorEntity(1L,"Alexandr Rudazov","44-55-66","3336 555333","male","1992-02-02");
        when(mockRepository.save(any(AuthorEntity.class))).thenReturn(updatedAuthorEntity);

        mockMvc.perform(put("/authors/1")
                .content(om.writeValueAsString(updatedAuthorEntity))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId", is(1)))
                .andExpect(jsonPath("$.author", is("Alexandr Rudazov")))
                .andExpect(jsonPath("$.phone", is("44-55-66")));


    }


    @Test
    public void delete_author_OK() throws Exception {

        doNothing().when(mockRepository).delete(any(AuthorEntity.class));

        mockMvc.perform(delete("/authors/1"))
                /*.andDo(print())*/
                .andExpect(status().isOk());

        verify(mockRepository, times(1)).delete(any(AuthorEntity.class));
    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}