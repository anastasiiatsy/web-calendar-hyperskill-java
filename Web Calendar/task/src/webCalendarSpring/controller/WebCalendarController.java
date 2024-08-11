package webCalendarSpring.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import webCalendarSpring.exceptionHandeling.GlobalExceptionHandler;
import webCalendarSpring.model.Event;
import webCalendarSpring.services.EventService;

import java.time.LocalDate;
import java.util.*;

@RestController
public class WebCalendarController {

    @Autowired
    private EventService eventService;

    @GetMapping("/event/today")
    public List<Event> getTodaysEvent() {
        return eventService.getAllByDate(LocalDate.now());
    }

    @GetMapping("/event")
    public ResponseEntity<?> getAllEvents(@RequestParam(required = false) String start_time,

                                          @RequestParam(required = false) String end_time) {

        List<Event> events;
        if (start_time != null && end_time != null) {
            LocalDate start = LocalDate.parse(start_time);
            LocalDate end = LocalDate.parse(end_time);
            events = eventService.getAllBetween(start, end);
        } else {
            events = eventService.getAll();
        }

        if (events.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        } else {
            return ResponseEntity
                    .ok()
                    .body(events);
        }
    }

    @GetMapping("/event/{id}")
    public ResponseEntity getEventById(@PathVariable("id") int id) {
        Event event = eventService.getById(id);
        Map<String, String> returnMessage = new HashMap<>();

        if (event == null) {
            returnMessage.put("message", "The event doesn't exist!");
            return new ResponseEntity<>(returnMessage, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity
                    .ok()
                    .body(event);
        }
    }

    @PostMapping("/event")
    @ResponseBody
    public ResponseEntity addEvent(@Valid @RequestBody Event event, BindingResult result) {

        if (result.hasErrors()) {
            return new GlobalExceptionHandler().handleBadRequestBodyException("", HttpStatus.BAD_REQUEST);

        } else {
            Map<String, String> response = new LinkedHashMap<>();
            response.put("message", "The event has been added!");
            response.put("event", event.getEvent());
            response.put("date", event.getDate().toString());
            eventService.save(event);
            return ResponseEntity
                    .ok()
                    .body(response);

        }
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity deleteEvent(@PathVariable("id") long id) {
        Event event = eventService.deleteById(id);
        Map<String, String> returnMessage = new HashMap<>();

        if (event == null) {
            returnMessage.put("message", "The event doesn't exist!");
            return new ResponseEntity<>(returnMessage, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity
                    .ok()
                    .body(event);
        }
    }


}


