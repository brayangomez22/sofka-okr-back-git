package co.com.sofka.okrs.controller.calendarcontroller;

import co.com.sofka.okrs.domain.calendarDomain.EventCalendar;
import co.com.sofka.okrs.service.calendarservice.CalendarService;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CalendarControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    CalendarService calendarService;

    @Test
    void loadCalendarPrimaryCalendar() throws GeneralSecurityException, IOException {


        Flux<Event> calFlux = webTestClient.get().uri("/calendar/list").exchange()
                .expectStatus().isOk()
                .returnResult(Event.class)
                .getResponseBody();
    }
    @Test
    void saveCalendarPrimaryCalendar() throws GeneralSecurityException, IOException {

        webTestClient.post().uri("/calendar/save").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .body(Mono.just(EventCalendar.DEFAULT_EVENT_CALENDAR), EventCalendar.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void loadFilterCalendarByEmail() throws GeneralSecurityException, IOException {
        String email = "example@gmail.com";
        Event event = new Event();
        EventAttendee attendee = new EventAttendee();
        attendee.setEmail(email);
        attendee.setDisplayName("example person");
        event.setAttendees(List.of(attendee));

        webTestClient.get().uri("/calendar/list".concat("/{email}"), email)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    void deletedCalendarEvent() throws GeneralSecurityException, IOException {

        webTestClient.delete().uri("/calendar/delete".concat("/{id}"), "ssxasdadxad21xada213")
                .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(Void.class);
    }
}