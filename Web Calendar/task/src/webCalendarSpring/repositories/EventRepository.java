package webCalendarSpring.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import webCalendarSpring.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByDate(@NotNull LocalDate date);

    List<Event> findByDateBetween(LocalDate startDate, LocalDate endDate);


}
