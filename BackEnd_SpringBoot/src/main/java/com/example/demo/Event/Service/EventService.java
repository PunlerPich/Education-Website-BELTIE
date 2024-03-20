package com.example.demo.Event.Service;


import com.example.demo.Event.Controller.EventRequest;
import com.example.demo.Event.Controller.EventResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventService {
    EventResponse createNews(EventRequest eventRequest, MultipartFile file) throws IOException, ChangeSetPersister.NotFoundException;
    List<EventResponse> getAllEvent(Sort.Direction direction, String sortBy,int page, int pageSize);
    EventResponse getEventById(Long eventId) throws ChangeSetPersister.NotFoundException;
    EventResponse updateEvent(Long eventId, EventRequest eventRequest, MultipartFile file) throws ChangeSetPersister.NotFoundException, IOException;
    void deleteEvent(Long eventId) throws ChangeSetPersister.NotFoundException;
}
