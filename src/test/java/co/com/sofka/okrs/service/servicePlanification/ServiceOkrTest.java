package co.com.sofka.okrs.service.servicePlanification;

import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceOkrTest {


    @InjectMocks
    ServiceOkr serviceOkr;

    @Mock
    RepositoryOkr repositoryOkr;

    @Test
    void saveOkr(){

        Okr okr = new Okr("xxx","terminar curso","hacer el curso",
                "daniel","d@gmail.com","da","aaaa","servicio",0.14d,
                List.of());

        when(repositoryOkr.save(okr)).thenReturn(Mono.just(okr));
        StepVerifier.create(serviceOkr.save(okr)).expectNext(okr).verifyComplete();


    }

    @Test
    void saveOkr_nullOkr(){

        Assertions.assertThrows(NullPointerException.class, () -> {
            serviceOkr.save(null);
        } );
    }

    @Test
    void getOkrs(){

        Okr okr = new Okr("xxx","tterminar curso","hacer el curso",
                "daniel","d@gmail.com","da","aaaa","servicio",0.14d,
                List.of());

        when(repositoryOkr.findOkrByUserId("da")).thenReturn(Flux.just(okr));
        StepVerifier.create(serviceOkr.findAll("da")).expectNext(okr).verifyComplete();

    }

    @Test
    void deleteOkr(){

        when(repositoryOkr.deleteById("xxx")).thenReturn(Mono.empty());
        StepVerifier.create(serviceOkr.delete("xxx")).expectNext().verifyComplete();
    }

    @Test
    @Order(2)
    void updateOkr() {

        Okr okr = new Okr("xxx", "terminar curso", "hacer el curso",
                "daniel", "d@gmail.com", "da", "aaaa", "servicio", 0.14d,
                List.of());

        when(repositoryOkr.save(okr)).thenReturn(Mono.just(okr));
        StepVerifier.create(serviceOkr.update(okr)).expectNext(okr).verifyComplete();

    }
}