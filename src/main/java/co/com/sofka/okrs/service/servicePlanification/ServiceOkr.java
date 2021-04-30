package co.com.sofka.okrs.service.servicePlanification;

import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.repository.RepositoryKr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ServiceOkr{

    @Autowired
    private RepositoryOkr repositoryOKr;

    @Autowired
    private RepositoryKr repositoryKr;

    public Flux<Okr> findAll(String userId) {
        return repositoryOKr.findOkrByUserId(userId);
    }

    public Mono<Okr> save(Okr okr) {
        return repositoryOKr.save(Objects.requireNonNull(okr));
    }

    public Mono<Okr> update(Okr okr) {
        return repositoryOKr.save(okr);
    }

    public Mono<Void> delete(String id) {
        return repositoryOKr.deleteById(id);
    }

    public Mono<Okr> updateAdvanceOkr(Okr okr) {

        Flux<Kr> okrsid = repositoryKr.findAll().filter(x -> x.getOkrId().equals(okr.getId()));

        Mono<Double> porcentaje = (okrsid.collect(Collectors.summingDouble(
                x -> x.getAdvanceKr() * x.getPercentageWeight()
        )));


        porcentaje.map(n -> {
            okr.setAdvanceOkr(Float.parseFloat(n.toString()));
            System.out.println(n);
        return n;
        });

        System.out.println(okr.getAdvanceOkr());
        return repositoryOKr.save(okr);

    }

}
