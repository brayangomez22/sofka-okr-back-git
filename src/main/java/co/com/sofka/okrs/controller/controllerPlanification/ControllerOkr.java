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

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Okr> findAll(@PathVariable("userId") String userId) {
        return userService.findAll(userId);
    }

    @PostMapping("/postOkr")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Okr> save(@RequestBody Okr okr) {
        return userService.save(okr);
    }

    @PutMapping("/updOkr")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Okr> update(@RequestBody Okr okr){
        return  userService.update(okr);
    }

    @DeleteMapping("/deleteOkr/{id}")
    public Mono<Void> delete(@PathVariable("id") String id){
        return  userService.delete(id);
    }
}

