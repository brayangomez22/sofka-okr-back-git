package co.com.sofka.okrs.repository;

import co.com.sofka.okrs.domain.Kr;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryKr extends ReactiveMongoRepository<Kr, String> {
}
