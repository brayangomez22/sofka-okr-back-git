package co.com.sofka.okrs.controller.controllerPlanification;

import co.com.sofka.okrs.controller.dashboardController.ControladorDashboard;
import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.repository.RepositoryKr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import co.com.sofka.okrs.repository.UserRepository;
import co.com.sofka.okrs.service.dashboardService.DashboardService;
import co.com.sofka.okrs.service.servicePlanification.ServiceKr;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.http.HttpHeaders;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ControllerKr.class)
@Import(ServiceKr.class)
class ControllerKrTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RepositoryOkr repositoryOKR;

    @MockBean
    private RepositoryKr repositoryKr;

    @Test
    void findAll() {
        Kr kr = new Kr("0001", "01", "KeyResult1", "Jhovan Espinal",
                "jhovan@sofkau.com", new Date(), new Date(), 0F, 20F, "descripion");

        when(repositoryKr.findAll()).thenReturn(Flux.just(kr));

        Flux<Kr> krListFlux = webTestClient.get().uri("/Krs")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk().returnResult(Kr.class).getResponseBody();

        StepVerifier.create(krListFlux).expectNextCount(1).verifyComplete();

        Mockito.verify(repositoryKr, times(1)).findAll();
    }

    @Test
    void save() {
        Kr kr = new Kr("0001", "01", "KeyResult1", "Jhovan Espinal",
                "jhovan@sofkau.com", new Date(), new Date(), 0F, 20F, "descripion");

        when(repositoryKr.save(kr)).thenReturn(Mono.just(kr));

        webTestClient.post().uri("/Krs/postKrs").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(kr)).exchange().expectStatus().isCreated();

        Mockito.verify(repositoryKr, times(1)).save(kr);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}