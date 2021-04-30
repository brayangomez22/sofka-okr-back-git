package co.com.sofka.okrs.controller.controllerPlanification;

import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import co.com.sofka.okrs.service.servicePlanification.ServiceOkr;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ControllerOkr.class)
@Import(ServiceOkr.class)
class ControllerOkrTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private RepositoryOkr repositoryOkr;


    @Test

    void getOkr(){

        Okr okr = new Okr("xxx","tterminar curso","hacer el curso",
                "daniel","d@gmail.com","da","aaaa","servicio",14.0,
                List.of());

        when(repositoryOkr.findAll()).thenReturn(Flux.just(okr));

        Flux<Okr> okrListaFlux =  webTestClient.get().uri("/Okrs")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk().returnResult(Okr.class).getResponseBody();


        StepVerifier.create(okrListaFlux).expectNextCount(1).verifyComplete();

        Mockito.verify(repositoryOkr, times(1)).findAll();

    }

    @Test
    void saveOkr(){

        Okr okr = new Okr("xxx","terminar curso","hacer el curso",
                "daniel","d@gmail.com","da","aaaa","servicio",14.0,
                List.of());

        when(repositoryOkr.save(okr)).thenReturn(Mono.just(okr));

        webTestClient.post().uri("/Okrs/postOkr").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(okr)).exchange().expectStatus().isCreated();

        Mockito.verify(repositoryOkr, times(1)).save(okr);

    }

}