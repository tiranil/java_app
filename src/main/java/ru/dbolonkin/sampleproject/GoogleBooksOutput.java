package ru.dbolonkin.sampleproject;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "totalItems",
        "items"
})

public class GoogleBooksOutput {

    @JsonProperty("totalItems")
    private Integer totalItems;
    @JsonProperty("items")
    private List<Items> items = null;


    @JsonProperty("totalItems")
    public Integer getTotalItems() {
        return totalItems;
    }

    @JsonProperty("totalItems")
    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public GoogleBooksOutput withTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    @JsonProperty("items")
    public List<Items> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<Items> items) {
        this.items = items;
    }

    public GoogleBooksOutput withItems(List<Items> items) {
        this.items = items;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "volumeInfo"
    })

    private static class Items {

        @JsonProperty("volumeInfo")
        private VolumeInfo volumeInfo;

        @JsonProperty("volumeInfo")
        public VolumeInfo getVolumeInfo() {
            return volumeInfo;
        }

        @JsonProperty("volumeInfo")
        public void setVolumeInfo(VolumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }

        public Items withVolumeInfo(VolumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
            return this;
        }


        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonPropertyOrder({
                "publisher",
                "publishedDate"
        })
        private static class VolumeInfo {

            @JsonProperty("publisher")
            private String publisher;
            @JsonProperty("publishedDate")
            private String publishedDate;

            @JsonProperty("publisher")
            public String getPublisher() {
                return publisher;
            }

            @JsonProperty("publisher")
            public void setPublisher(String publisher) {
                this.publisher = publisher;
            }

            @JsonProperty("publishedDate")
            public String getPublishedDate() {
                return publishedDate;
            }

            @JsonProperty("publishedDate")
            public void setPublishedDate(String publishedDate) {
                this.publishedDate = publishedDate;
            }


        }

    }
}
