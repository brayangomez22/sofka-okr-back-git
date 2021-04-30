package co.com.sofka.okrs.controller.controllerPlanification;

import co.com.sofka.okrs.controller.dashboardController.ControladorDashboard;
import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.repository.RepositoryKr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import co.com.sofka.okrs.repository.UserRepository;
import co.com.sofka.okrs.service.servicePlanification.ServiceKr;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

//@ExtendWith(SpringExtension.class)
//@WebFluxTest(controllers = ControllerKr.class)
//@Import(ServiceKr.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ControllerKrTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ServiceKr serviceKr;

    @MockBean
    private RepositoryOkr repositoryOKR;

    @MockBean
    private RepositoryKr repositoryKr;

    @Test
    void findAll() {
        Kr kr = new Kr("0001", "01", "KeyResult1", "Jhovan Espinal",
                "jhovan@sofkau.com", new Date(), new Date(), 45d, 20d, "descripion");

        when(serviceKr.findAll("01")).thenReturn(Flux.just(kr));

        webTestClient.get().uri("/Krs/".concat("{okrId}"), "01").exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }


    @Test
    void save() {
        Kr kr = new Kr("0001", "01", "KeyResult1", "Jhovan Espinal",
                "jhovan@sofkau.com", new Date(), new Date(), 45d, 20d, "descripion");

        when(serviceKr.save( kr)).thenReturn(Mono.just(kr));
        when(repositoryKr.save(kr)).thenReturn(Mono.just(kr));

        webTestClient.post().uri("/Krs/postKr").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .body(Mono.just(kr), Kr.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Kr.class);
    }


    @Test
    void deleteKr(){


        when(repositoryKr.deleteById("xxx")).thenReturn(Mono.empty());

        webTestClient.delete().uri("/Krs/deleteKr".concat("/{id}"),"xxx")
                .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }

    @Test
    void updateKr() {
        Kr kr = new Kr("0001", "01", "KeyResult1", "Jhovan Espinal",
                "jhovan@sofkau.com", new Date(), new Date(), 45d, 20d, "descripion");

        when(serviceKr.save( kr)).thenReturn(Mono.just(kr));
        when(repositoryKr.save(kr)).thenReturn(Mono.just(kr));

        webTestClient.put().uri("/Krs/updKr").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .body(Mono.just(kr), Kr.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Kr.class);
    }

}