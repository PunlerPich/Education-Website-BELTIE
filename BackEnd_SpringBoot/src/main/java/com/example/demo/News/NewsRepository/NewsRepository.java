package com.example.demo.News.NewsRepository;

import com.example.demo.News.NewModel.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n JOIN FETCH n.category")
    List<News> findAllWithCategory();

    @Query("SELECT n FROM News n JOIN FETCH n.category c WHERE c.name = :categoryName")
    List<News> findAllByCategoryNameWithCategory(@Param("categoryName") String categoryName);

    List<News> findAllByCategory(String category);

    Page<News> findByCategoryId(Long categoryId, Pageable pageable);

    List<News> findByCategoryNameOrderByDateDesc(String category, Pageable pageable);

    List<News> findAllByOrderByDateDesc(Pageable pageable);

    List<News> findByCategoryName(String category, Pageable pageable);
    @Query("SELECT n FROM News n JOIN FETCH n.category")
    List<News> findByCategoryNameJoinCategory(String category, Pageable pageable);

    @Query("SELECT n FROM News n JOIN FETCH n.category")
    List<News> findAllJoinCategory(Pageable pageable);

    @Query("SELECT n FROM News n JOIN n.category c WHERE c.id = :categoryId")
    Page<News> findByCategoryIdJoinCategory(@Param("categoryId") Long categoryId, Pageable pageable);
}