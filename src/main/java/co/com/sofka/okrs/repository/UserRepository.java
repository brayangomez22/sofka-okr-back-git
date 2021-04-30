package co.com.sofka.okrs.repository;

import co.com.sofka.okrs.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends ReactiveMongoRepository<User, String>  {

}
