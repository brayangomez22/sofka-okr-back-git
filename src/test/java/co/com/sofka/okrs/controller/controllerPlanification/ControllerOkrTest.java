package co.com.sofka.okrs.controller.controllerPlanification;

import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import co.com.sofka.okrs.service.servicePlanification.ServiceOkr;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
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

//@ExtendWith(SpringExtension.class)
//@WebFluxTest(controllers = ControllerOkr.class)
//@Import(ServiceOkr.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ControllerOkrTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private RepositoryOkr repositoryOkr;


    @Test
    void getOkr(){
        Okr okr = new Okr("aaaa","tterminar curso","hacer el curso",
                "daniel","d@gmail.com","bbbb","aaaa","servicio",40d,

                List.of());

        when(repositoryOkr.findOkrByUserId("aaaa")).thenReturn(Flux.just(okr));

        Flux<Okr> okrListaFlux =  webTestClient.get().uri("/Okrs/{userId}", "aaaa")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk().returnResult(Okr.class).getResponseBody();


        StepVerifier.create(okrListaFlux).expectNextCount(1).verifyComplete();

        Mockito.verify(repositoryOkr, times(1)).findOkrByUserId("aaaa");
    }

    @Test
    void saveOkr(){

        Okr okr = new Okr("xxx","terminar curso","hacer el curso",
                "daniel","d@gmail.com","da","aaaa","servicio",0.14d,
                List.of());

        when(repositoryOkr.save(okr)).thenReturn(Mono.just(okr));

        webTestClient.post().uri("/Okrs/postOkr").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(okr)).exchange().expectStatus().isCreated();

        Mockito.verify(repositoryOkr, times(1)).save(okr);

    }

    @Test
    void deleteOkr(){


        when(repositoryOkr.deleteById("xxx")).thenReturn(Mono.empty());

        webTestClient.delete().uri("/Okrs/deleteOkr".concat("/{id}"),"xxx")
                .accept(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }

    @Test
    void updateOkr() {

        Okr okr = new Okr("xxx", "terminar curso", "hacer el curso",
                "daniel", "d@gmail.com", "da", "aaaa", "servicio", 0.14d,
                List.of());

        when(repositoryOkr.save(okr)).thenReturn(Mono.just(okr));

        webTestClient.put().uri("/Okrs/updOkr").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(okr)).exchange().expectStatus().isCreated();

        Mockito.verify(repositoryOkr, times(1)).save(okr);
    }
}