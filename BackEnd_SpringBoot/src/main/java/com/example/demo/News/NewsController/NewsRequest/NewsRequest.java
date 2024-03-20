package com.example.demo.News.NewsController.NewsRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest {
    private String title;
    private String content;
    private String author;
    private String key;
    private Long categoryId;
    private boolean saved;




}