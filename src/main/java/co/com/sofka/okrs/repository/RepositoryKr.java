package co.com.sofka.okrs.repository;

import co.com.sofka.okrs.domain.Kr;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RepositoryKr extends ReactiveMongoRepository<Kr, String> {

    Flux<Kr> findByOkrId(String okrId);
    Mono<Kr> findFirstByOkrIdOrderByFinishDateDesc(String okrId);
    Mono<Kr> findFirstByOkrIdOrderByFinishDate(String okrId);

}
