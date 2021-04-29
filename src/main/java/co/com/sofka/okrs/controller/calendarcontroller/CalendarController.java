package co.com.sofka.okrs.controller.calendarcontroller;
import co.com.sofka.okrs.domain.calendarDomain.EventCalendar;
import co.com.sofka.okrs.service.calendarservice.*;


import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @PostMapping("/save")
    public Mono<Event> saveEventKR(@RequestBody EventCalendar eventCalendar) throws IOException, GeneralSecurityException {
       return calendarService.save(eventCalendar);
    }


    @GetMapping("/list")
    public Flux<Event> loadCalendar() throws IOException, GeneralSecurityException {
        return calendarService.load();
    }

    @GetMapping("/list/{email}")
    public Flux<Event> loadFilter(@PathVariable String email) throws IOException, GeneralSecurityException {
        return calendarService.loadFilter(email);
    }


    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable String id) throws IOException, GeneralSecurityException {
        return calendarService.delete(id);
    }
}
