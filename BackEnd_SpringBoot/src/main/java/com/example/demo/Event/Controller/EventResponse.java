package com.example.demo.Event.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private Long id;

    private String title;

    private String content;

    private LocalDate date;

    private String imageUrl;
}
