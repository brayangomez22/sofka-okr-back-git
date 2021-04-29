package co.com.sofka.okrs.controller.controllerPlanification;

import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.dto.okrs_dto.OkrCollect;
import co.com.sofka.okrs.service.servicePlanification.ServiceOkr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/Okrs")
@CrossOrigin("*")
public class ControllerOkr {
    @Autowired
    private ServiceOkr serviceOkr;

    @GetMapping
    public Flux<Okr> findAll() {
        return serviceOkr.findAll();
    }

    @PostMapping("/postOkr")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Okr> save(@RequestBody Okr okr) {
        return serviceOkr.save(okr);
    }


    /*@GetMapping("/getData")
    public Flux<Okr> getData() {
        return serviceOkr.getData();
    }*/

    @GetMapping("/test")
    public List<Kr> test() {
        return serviceOkr.test();
    }


    }