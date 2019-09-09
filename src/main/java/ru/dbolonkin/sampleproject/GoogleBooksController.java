package ru.dbolonkin.sampleproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/search")
public class GoogleBooksController {

    private static HttpURLConnection con;

    // Поиск по слову(word) суказанием доп.параметров (query)

    @GetMapping
    public List<String> searchBooksQuery(@RequestParam("word") String word, @RequestParam("query") String query) throws IOException {
        List<String> googleBooksList = new ArrayList<>();
        String name = URLEncoder.encode(String.valueOf(word), "UTF-8");
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + name + "&projection=lite&key=AIzaSyDZv6UaewJWXklkqGQV7goY-jhPUW6ZbQ4&fields="+query;

        try {
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestMethod("GET");


            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;

                while ((line = in.readLine()) != null) {
                    googleBooksList.add(line);
                }
            }

            return googleBooksList;

        } finally {

            con.disconnect();
        }

    }

    // Общий поиск по слову
    @GetMapping("/all")
    public List<String> searchAllBooks (@RequestParam("word") String word) throws IOException{
        List<String> googleBooksList = new ArrayList<>();
        String name = URLEncoder.encode(String.valueOf(word), "UTF-8");
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + name + "&projection=lite&key=AIzaSyDZv6UaewJWXklkqGQV7goY-jhPUW6ZbQ4";

        try {
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestMethod("GET");


            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;

                while ((line = in.readLine()) != null) {
                    googleBooksList.add(line);
                }
            }

            return googleBooksList;

        } finally {

            con.disconnect();
        }
    }
}
