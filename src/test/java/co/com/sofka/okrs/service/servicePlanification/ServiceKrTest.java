package co.com.sofka.okrs.service.servicePlanification;

import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.repository.RepositoryKr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceKrTest {

    @InjectMocks
    ServiceKr serviceKr;

    @Mock
    RepositoryKr repositoryKr;

    @Test
    void save(){
        Kr kr = new Kr("0001", "01", "KeyResult1", "Jhovan Espinal",
                "jhovan@sofkau.com", new Date(), new Date(), 0D, 20D, "descripion");

        when(repositoryKr.save(kr)).thenReturn(Mono.just(kr));

        StepVerifier.create(serviceKr.save(kr)).expectNext(kr).verifyComplete();
    }

    @Test
    void saveKr_nullKr(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            serviceKr.save(null);
        });
    }

    @Test
    void getKrs(){
        Kr kr = new Kr("0001", "01", "KeyResult1", "Jhovan Espinal",
                "jhovan@sofkau.com", new Date(), new Date(), 0D, 20D, "descripion");

        when(repositoryKr.findByOkrId("01")).thenReturn(Flux.just(kr));

        StepVerifier.create(serviceKr.findAll("01")).expectNext(kr).verifyComplete();

    }

    @Test
    void update(){
        Kr kr = new Kr("0002", "01", "primero", "Persona 1",
                "persona1@sofkau.com", new Date(), new Date(), 0D, 20D, "descripion");
        when(serviceKr.update(kr)).thenReturn(Mono.just(kr));

        StepVerifier.create(serviceKr.update(kr)).expectNext(kr).verifyComplete();
    }

    @Test
    void delete(){
        when(serviceKr.delete("0002")).thenReturn(Mono.empty());
        StepVerifier.create(serviceKr.delete("0002")).verifyComplete();
    }

}