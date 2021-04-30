package co.com.sofka.okrs.service.servicePlanification;

import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.repository.RepositoryKr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ServiceKr {
    @Autowired
    private RepositoryKr repositoryKr;

    public Flux<Kr> findAll(String okrId) {
        return repositoryKr.findByOkrId(okrId);
    }


    public Mono<Kr> save(Kr kr) {

        return repositoryKr.save(Objects.requireNonNull(kr));
    }

    public Mono<Kr> update(Kr kr) {
        return repositoryKr.save(kr);
    }

    public Mono<Void> delete(String id) {
        return repositoryKr.deleteById(id);
    }


}
