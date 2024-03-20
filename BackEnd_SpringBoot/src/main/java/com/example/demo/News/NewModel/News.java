package com.example.demo.News.NewModel;
import com.example.demo.Category.CatetoryModel.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        @Lob
        @Column(columnDefinition = "LONGTEXT")
        private String content;

        private String author;

        private LocalDate date;

        private String imageUrl;

        private String categoryName;

        private boolean saved;

        @ManyToOne
        @JoinColumn(name = "category_id")
        private Category category;

        // Constructors, getters, and setters



}