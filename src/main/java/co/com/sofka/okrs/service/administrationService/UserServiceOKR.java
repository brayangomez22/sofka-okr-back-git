package co.com.sofka.okrs.service.administrationService;

import co.com.sofka.okrs.domain.User;
import co.com.sofka.okrs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class UserServiceOKR {
    @Autowired
    private UserRepository repository;

    public Mono<User> save(User user){
       // return repository.findById(user.getId()).switchIfEmpty(repository.save(user));
        return repository.
                findById(user.getId()).map(user1 -> {
                    System.out.println(user1);
                    return user1;}).
                switchIfEmpty(repository.save(user));



    }


}
