package com.example.demo.News.NewsController;

import com.example.demo.News.NewsController.NewsRequest.NewsRequest;
import com.example.demo.News.NewsController.NewsResponse.NewsResponse;
import com.example.demo.News.NewsService.NewsService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "http://localhost:3000")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping("/admin/createNews")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<NewsResponse> createNews(NewsRequest newsRequest,MultipartFile file) throws ChangeSetPersister.NotFoundException, IOException {
        NewsResponse createdNews = newsService.createNews(newsRequest,file);
        return new ResponseEntity<>(createdNews, HttpStatus.CREATED);
    }



    @GetMapping("news/{newsId}")
    public ResponseEntity<NewsResponse> getNewsById(@PathVariable Long newsId) {
        try {
            NewsResponse newsResponse = newsService.getNewsById(newsId);
            return ResponseEntity.ok(newsResponse);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            // Handle IO exception appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<NewsResponse>> getNewsByCategoryId(
        @PathVariable("categoryId") Long categoryId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "5") int size
    ) throws ChangeSetPersister.NotFoundException {
    Pageable pageable = PageRequest.of(page, size);
    Page<NewsResponse> newsPage = newsService.getNewsByCategoryId(categoryId, pageable);

    if (newsPage.isEmpty()) {
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.ok(newsPage);
    }
    }
    @PostMapping("/saveForLater")
    public ResponseEntity<String> saveNewsForLater(@RequestBody NewsRequest newsRequest) {
        // Save the news article and set the saved field based on newsRequest.isSaved()

        return new ResponseEntity<>("News saved for later", HttpStatus.OK);
    }
    @GetMapping("/getAllNews")
    public List<NewsResponse> getAllNews (
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) throws ChangeSetPersister.NotFoundException {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return newsService.getAllNewsSortedByDateAndCategory(category, sortBy, direction, page, pageSize);
    }
    @PutMapping("Update/{newsId}")
    public ResponseEntity<NewsResponse> updateNews(
            @PathVariable Long newsId,
            NewsRequest newsRequest,
            @RequestParam(required = false) MultipartFile file) throws ChangeSetPersister.NotFoundException, IOException {

        NewsResponse updatedNews = newsService.updateNews(newsId, newsRequest, file);
        return ResponseEntity.ok(updatedNews);
    }
    @DeleteMapping("Delete/{newsId}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long newsId) throws ChangeSetPersister.NotFoundException {
        newsService.deleteNews(newsId);
        return ResponseEntity.noContent().build();
    }
}