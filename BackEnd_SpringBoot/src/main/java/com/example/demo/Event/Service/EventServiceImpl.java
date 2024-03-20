package com.example.demo.Event.Service;

import com.example.demo.Event.Controller.EventRequest;
import com.example.demo.Event.Controller.EventResponse;
import com.example.demo.Event.Model.Event;
import com.example.demo.Event.Model.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService{
    @Autowired
    private final EventRepository eventRepository;
    private final S3Client s3Client;
    private final String bucketName = "mynewsforbelty";


    @Override
    public EventResponse createNews(EventRequest eventRequest, MultipartFile file) throws IOException, ChangeSetPersister.NotFoundException {
        String filename = file.getOriginalFilename();
        String key = "TestImage/" + filename;
        String imageUrl = "https://mynewsforbelty.s3.amazonaws.com/TestImage/" + filename;

        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build(), RequestBody.fromBytes(file.getBytes()));

        Event event = new Event();
        event.setTitle(eventRequest.getTitle());
        event.setContent(eventRequest.getContent());
        event.setImageUrl(imageUrl);
        event.setDate(LocalDate.now());
        event = eventRepository.save(event);
        return convertToEventResponse(event);
    }

    @Override
    public List<EventResponse> getAllEvent(Sort.Direction direction, String sortBy, int page, int pageSize) {
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Event> eventPage = eventRepository.findAll(pageable);
        List<Event> events = eventPage.getContent();
        List<EventResponse> eventResponses = new ArrayList<>();
        for (Event event : events) {
            EventResponse response = convertToEventResponse(event);
            eventResponses.add(response);
        }
        return eventResponses;
    }

    @Override
    public EventResponse getEventById(Long eventId) throws ChangeSetPersister.NotFoundException {
        Event event= eventRepository.findById(eventId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        return convertToEventResponse(event);


    }

    @Override
    public EventResponse updateEvent(Long eventId, EventRequest eventRequest, MultipartFile file) throws ChangeSetPersister.NotFoundException, IOException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Update the news properties with the new values from the request
        event.setTitle(eventRequest.getTitle());
        event.setContent(eventRequest.getContent());
        event.setDate(LocalDate.now());

        // Update the image URL if a file is provided
        if (file != null && !file.isEmpty()) {
            String filename = file.getOriginalFilename();
            String key = "TestImage/" + filename;
            String imageUrl = "https://mynewsforbelty.s3.amazonaws.com/TestImage/" + filename;

            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build(), RequestBody.fromBytes(file.getBytes()));

            event.setImageUrl(imageUrl);
        }
        event=eventRepository.save(event);
        return convertToEventResponse(event);
    }

    @Override
    public void deleteEvent(Long eventId) throws ChangeSetPersister.NotFoundException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        eventRepository.delete(event);
    }

    private EventResponse convertToEventResponse (Event event){
        EventResponse response = new EventResponse();
        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setContent(event.getContent());
        response.setDate(event.getDate());
        response.setImageUrl(event.getImageUrl());
        return response;
    }

}
