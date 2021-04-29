package co.com.sofka.okrs.service.servicePlanification;

import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class ServiceOkr{
    @Autowired
    private RepositoryOkr repositoryOKr;



    public Flux<Okr> findAll() {
        return repositoryOKr.findAll();
    }


    public Mono<Okr> save(Okr okr) {
        return repositoryOKr.save(Objects.requireNonNull(okr));
    }



}
