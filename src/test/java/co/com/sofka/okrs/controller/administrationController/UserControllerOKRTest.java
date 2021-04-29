package co.com.sofka.okrs.controller.administrationController;

import co.com.sofka.okrs.domain.User;
import co.com.sofka.okrs.repository.UserRepository;
import co.com.sofka.okrs.service.administrationService.UserServiceOKR;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserControllerOKR.class)
@Import(UserServiceOKR.class)
class UserControllerOKRTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    UserRepository userRepository;

    @Test
    void saveUser(){

        User user = new User("xxx", "Juan","juan@example.com");
        when(userRepository.findById("xxx")).thenReturn(Mono.empty());
        when(userRepository.save(user)).thenReturn(Mono.just(user));
        webTestClient.post().uri("/api/users").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(user)).exchange().expectStatus().isCreated();

        Mockito.verify(userRepository, times(1)).save(user);

    }

}