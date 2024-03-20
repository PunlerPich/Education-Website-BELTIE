package com.example.demo.Event.Controller;

import com.example.demo.Event.Service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("news/")
public class EventController {
    private final EventService eventService;
    @PostMapping("/admin/createEvent")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<EventResponse>createNews(EventRequest eventRequest, MultipartFile file) throws IOException, ChangeSetPersister.NotFoundException{
        EventResponse createNews = eventService.createNews(eventRequest,file);
        return new ResponseEntity<>(createNews, HttpStatus.CREATED);
    }
    @GetMapping("getAllEvent")
    public ResponseEntity<List<EventResponse>> getAllEvent(
            @RequestParam(required = false,defaultValue = "ASC") String direction,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int pageSize){
        Sort.Direction sortDirection= Sort.Direction.fromString(direction.toUpperCase());
        List<EventResponse> events= eventService.getAllEvent(sortDirection,sortBy,page,pageSize);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long eventId) throws ChangeSetPersister.NotFoundException{
        EventResponse event = eventService.getEventById(eventId);
        return ResponseEntity.ok(event);
    }


    @PutMapping("Update/event/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long eventId,
            EventRequest eventRequest,
            @RequestParam(required = false)MultipartFile file)throws ChangeSetPersister.NotFoundException, IOException{
        EventResponse updateEvent = eventService.updateEvent(eventId,eventRequest,file);
        return ResponseEntity.ok(updateEvent);
    }
    @DeleteMapping("Delete/event/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) throws ChangeSetPersister.NotFoundException {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}
