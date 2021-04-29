package co.com.sofka.okrs.controller.controllerPlanification;

import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.service.servicePlanification.ServiceKr;
import co.com.sofka.okrs.service.servicePlanification.ServiceOkr;
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
    private ServiceKr userService;

    @GetMapping
    public Flux<Kr> findAll() {
        return userService.findAll();
    }

    @PostMapping("/postKr")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Kr> save(@RequestBody Kr kr){
        return  userService.save(kr);
    }

    @PutMapping("/updKr")
    public Mono<Kr> update(@RequestBody Kr kr){
        return  userService.update(kr);
    }

    @DeleteMapping("/deleteKr/{id}")
    public Mono<Void> delete(@PathVariable("id") String id){
        return  userService.delete(id);
    }

}
