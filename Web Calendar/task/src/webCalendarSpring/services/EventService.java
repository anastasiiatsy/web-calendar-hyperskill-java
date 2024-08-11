package webCalendarSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webCalendarSpring.repositories.EventRepository;
import webCalendarSpring.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllByDate(LocalDate date) {
        return eventRepository.findByDate(date);
    }

    public void save(Event event) {
        eventRepository.save(event);
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public List<Event> getAllBetween(LocalDate startDate, LocalDate endDate) {
        return eventRepository.findByDateBetween(startDate, endDate);
    }

    public Event getById(long id) {
        Event event = eventRepository.findById(id).orElse(null);
        return event;
    }

    public Event deleteById(long id) {
        Event event = getById(id);
        if (event != null) {
            eventRepository.deleteById(id);
        }
        return event;
    }

}
