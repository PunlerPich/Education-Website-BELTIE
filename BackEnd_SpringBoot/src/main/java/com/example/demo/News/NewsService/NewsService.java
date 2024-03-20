package com.example.demo.News.NewsService;

import com.example.demo.News.NewsController.NewsRequest.NewsRequest;
import com.example.demo.News.NewsController.NewsResponse.NewsResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NewsService {
 NewsResponse createNews(NewsRequest newsRequest,MultipartFile file) throws IOException, ChangeSetPersister.NotFoundException;
 NewsResponse getNewsById(Long newsId) throws ChangeSetPersister.NotFoundException, IOException;


 Page<NewsResponse> getNewsByCategoryId(Long categoryId, Pageable pageable) throws ChangeSetPersister.NotFoundException;
 List<NewsResponse> getAllNewsSortedByDateAndCategory(String category, String sortBy, Sort.Direction sortOrder, int page, int pageSize) throws ChangeSetPersister.NotFoundException;
 NewsResponse updateNews(Long newsId, NewsRequest newsRequest, MultipartFile file) throws ChangeSetPersister.NotFoundException, IOException;
 void deleteNews(Long newsId) throws ChangeSetPersister.NotFoundException;
}