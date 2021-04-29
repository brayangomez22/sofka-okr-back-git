package co.com.sofka.okrs.controller.controllerPlanification;

import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.service.servicePlanification.ServiceOkr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Okrs")
@CrossOrigin("*")
public class ControllerOkr {
    @Autowired
    private ServiceOkr userService;

    @GetMapping
    public Flux<Okr> findAll() {
        return userService.findAll();
    }

    @PostMapping("/postOkr")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Okr> save(@RequestBody Okr okr) {
        return userService.save(okr);
    }


}