package co.com.sofka.okrs.service.administrationService;

import co.com.sofka.okrs.domain.User;
import co.com.sofka.okrs.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceOKRTest {

    @InjectMocks
    UserServiceOKR userServiceOKR;

    @Mock
    UserRepository userRepository;

    @Test
    void saveUser(){
        User user = new User("xxx","Felipe","juan@example.com");
        when(userRepository.findById("xxx")).thenReturn(Mono.empty());
        when(userRepository.save(user)).thenReturn(Mono.just(user));
        StepVerifier.create(userServiceOKR.save(user)).expectNext(user).verifyComplete();
    }

    @Test
    void saveUser_null(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            userServiceOKR.save(null);
        });
    }


}