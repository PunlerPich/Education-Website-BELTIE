package com.example.demo.Event.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {
    private String title;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;


    private String imageUrl;
}
