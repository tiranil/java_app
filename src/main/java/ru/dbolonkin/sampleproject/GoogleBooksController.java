package ru.dbolonkin.sampleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;



@RestController
public class GoogleBooksController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/search")
    public GoogleBooksOutput getBooksList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<GoogleBooksOutput> entity = new HttpEntity<GoogleBooksOutput>(headers);

        return restTemplate.exchange("https://www.googleapis.com/books/v1/volumes?q=Dark&projection=lite&key=AIzaSyDZv6UaewJWXklkqGQV7goY-jhPUW6ZbQ4", HttpMethod.GET, entity, GoogleBooksOutput.class).getBody();
    }
}
