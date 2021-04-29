package co.com.sofka.okrs.service.servicePlanification;

import co.com.sofka.okrs.domain.Kr;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IServiceKr {

    Flux<Kr> findAll();
    Mono<Kr> save(Kr kr);
    Mono<Void> delete(String id);

}
