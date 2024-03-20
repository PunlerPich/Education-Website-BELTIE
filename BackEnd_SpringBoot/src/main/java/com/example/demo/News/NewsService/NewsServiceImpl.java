package com.example.demo.News.NewsService;


import com.example.demo.News.NewsController.NewsRequest.NewsRequest;
import com.example.demo.Category.CategoryController.Response.CategoryResponse;
import com.example.demo.News.NewsController.NewsResponse.NewsResponse;
import com.example.demo.Category.CatetoryModel.Category;
import com.example.demo.News.NewModel.News;
import com.example.demo.News.NewsRepository.NewsRepository;
import com.example.demo.Category.CategoryService.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {
    @Autowired
    private final S3Client s3Client;
    private final String bucketName = "mynewsforbelty";
    private final NewsRepository newsRepository;
    private final CategoryService categoryService;



    @Override
    public NewsResponse createNews(NewsRequest newsRequest, MultipartFile file) throws ChangeSetPersister.NotFoundException, IOException {
        CategoryResponse categoryResponse = categoryService.getCategoryById(newsRequest.getCategoryId());
        String filename = file.getOriginalFilename();
        String key = "TestImage/" + filename;
        String imageUrl = "https://mynewsforbelty.s3.amazonaws.com/TestImage/" + filename;

        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build(), RequestBody.fromBytes(file.getBytes()));


        News news = new News();
        news.setTitle(newsRequest.getTitle());
        news.setContent(newsRequest.getContent());
        news.setAuthor(newsRequest.getAuthor());
        news.setDate(LocalDate.now());
        news.setImageUrl(imageUrl);
        news.setCategoryName(categoryResponse.getName());
        news.setCategory(convertToCategoryEntity(categoryResponse));
        news.setSaved(newsRequest.isSaved());
        news = newsRepository.save(news);
        return convertToNewsResponse(news, categoryResponse);
    }

    // Utility method to convert CategoryResponse to Category entity
    private Category convertToCategoryEntity(CategoryResponse categoryResponse) {
        Category category = new Category();
        category.setId(categoryResponse.getId());
        category.setName(categoryResponse.getName());
        return category;
    }

    // Utility method to convert News entity, ImageResponse, and CategoryResponse to NewsResponse
    private NewsResponse convertToNewsResponse(News news, CategoryResponse categoryResponse) {
        NewsResponse response = new NewsResponse();
        response.setId(news.getId());
        response.setTitle(news.getTitle());
        response.setContent(news.getContent());
        response.setAuthor(news.getAuthor());
        response.setDate(news.getDate());
        response.setImageUrl(news.getImageUrl());
        response.setCategoryName(news.getCategoryName());
        response.setCategory(categoryResponse);
        response.setSaved(news.isSaved());
        return response;
    }
    @Override
    public List<NewsResponse> getAllNewsSortedByDateAndCategory(String category, String sortBy, Sort.Direction sortOrder, int page, int pageSize) throws ChangeSetPersister.NotFoundException{
        Sort sort = Sort.by(sortOrder, sortBy);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        List<News> newsList;
        if (category != null && !category.isEmpty()) {
            newsList = newsRepository.findByCategoryNameJoinCategory(category, pageable);
        } else {
            newsList = newsRepository.findAllJoinCategory(pageable);
        }

        List<NewsResponse> newsResponses = new ArrayList<>();
        for (News news : newsList) {
            CategoryResponse categoryResponse = convertToCategoryResponse(news.getCategory());
            NewsResponse newsResponse = convertToNewsResponse(news, categoryResponse);
            newsResponses.add(newsResponse);
        }

        return newsResponses;
    }

    @Override
    public NewsResponse getNewsById(Long newsId) throws ChangeSetPersister.NotFoundException {
        Optional<News> newsOptional = newsRepository.findById(newsId);
        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            CategoryResponse categoryResponse = convertToCategoryResponse(news.getCategory());
            return convertToNewsResponse(news, categoryResponse);
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }
    @Override
    public Page<NewsResponse> getNewsByCategoryId(Long categoryId, Pageable pageable) throws ChangeSetPersister.NotFoundException {
        Page<News> newsPage = newsRepository.findByCategoryIdJoinCategory(categoryId, pageable);

        CategoryResponse categoryResponse = convertToCategoryResponse(newsPage.getContent().get(0).getCategory());

        return newsPage.map(news -> convertToNewsResponse(news, categoryResponse));
    }
    // Utility method to convert Category entity to CategoryResponse
    private CategoryResponse convertToCategoryResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }
    @Override
    public NewsResponse updateNews(Long newsId, NewsRequest newsRequest, MultipartFile file) throws ChangeSetPersister.NotFoundException, IOException {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Update the news properties with the new values from the request
        news.setTitle(newsRequest.getTitle());
        news.setContent(newsRequest.getContent());
        news.setAuthor(newsRequest.getAuthor());

        // Update the image URL if a file is provided
        if (file != null && !file.isEmpty()) {
            String filename = file.getOriginalFilename();
            String key = "TestImage/" + filename;
            String imageUrl = "https://mynewsforbelty.s3.amazonaws.com/TestImage/" + filename;

            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build(), RequestBody.fromBytes(file.getBytes()));

            news.setImageUrl(imageUrl);
        }

        // Update the category if provided in the request
        if (newsRequest.getCategoryId() != null) {
            CategoryResponse categoryResponse = categoryService.getCategoryById(newsRequest.getCategoryId());
            news.setCategoryName(categoryResponse.getName());
            news.setCategory(convertToCategoryEntity(categoryResponse));
        }

        news = newsRepository.save(news);

        // Retrieve the updated CategoryResponse from the news entity
        CategoryResponse categoryResponse = convertToCategoryResponse(news.getCategory());

        // Convert the updated news entity to response
        return convertToNewsResponse(news, categoryResponse);
    }
    @Override
    public void deleteNews(Long newsId) throws ChangeSetPersister.NotFoundException {
        News news = newsRepository.findById(newsId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        newsRepository.delete(news);
    }
    public void saveNewsForLater(Long newsId) throws ChangeSetPersister.NotFoundException {
        News news = newsRepository.findById(newsId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        news.setSaved(true);
        newsRepository.save(news);
    }
}