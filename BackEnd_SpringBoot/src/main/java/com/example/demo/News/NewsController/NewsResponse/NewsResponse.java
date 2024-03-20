package com.example.demo.News.NewsController.NewsResponse;

import com.example.demo.Category.CategoryController.Response.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDate date;
    private String imageUrl;
    private String categoryName;
    private CategoryResponse category;
    private boolean saved;




}