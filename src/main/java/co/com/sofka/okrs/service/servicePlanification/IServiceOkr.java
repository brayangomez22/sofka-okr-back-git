package co.com.sofka.okrs.service.servicePlanification;

import co.com.sofka.okrs.domain.Okr;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IServiceOkr {

    Flux<Okr> findAll();
    Mono<Okr> save(Okr okr);
    Mono<Okr> update(Okr okr);
    Mono<Void> delete(String id);
}
