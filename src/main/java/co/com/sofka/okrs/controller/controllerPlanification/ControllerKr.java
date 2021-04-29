package co.com.sofka.okrs.controller.controllerPlanification;

import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.service.servicePlanification.ServiceKr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Krs")
@CrossOrigin("*")
public class ControllerKr {
    @Autowired
    private ServiceKr serviceKr;

    @GetMapping
    public Flux<Kr> findAll() {
        return serviceKr.findAll();
    }

    @PostMapping("/postkr")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Kr> save(@RequestBody Kr kr){
        return  serviceKr.save(kr);
    }

    @PutMapping("/updkr")
    public Mono<Kr> update(@RequestBody Kr kr){
        return  serviceKr.save(kr);
    }

    @DeleteMapping("/deletekr/{id}")
    public Mono<Void> delete(@PathVariable("id") String id){
        return  serviceKr.delete(id);
    }

    @GetMapping("/getKrs/{id}")
    public Flux<Kr> findByOkrId(@PathVariable("id") String okrId) {
        return serviceKr.findByOkrId(okrId);
    }

    }
