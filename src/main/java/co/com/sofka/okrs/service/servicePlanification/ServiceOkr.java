package co.com.sofka.okrs.service.servicePlanification;

import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.dto.dashboard_dto.OkrTable;
import co.com.sofka.okrs.dto.okrs_dto.OkrCollect;
import co.com.sofka.okrs.repository.RepositoryKr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import co.com.sofka.okrs.utils.planificationUtils.Assembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class ServiceOkr{
    @Autowired
    private RepositoryOkr repositoryOKr;

    @Autowired
    private RepositoryKr repositoryKr;

    public Flux<Okr> findAll() {
        return repositoryOKr.findAll();
    }

    public Mono<Okr> save(Okr okr) {
        return repositoryOKr.save(Objects.requireNonNull(okr));
    }

    public Mono<Okr> update(Okr okr) {
        repositoryOKr.findAll().filter(x -> x.getId().equals(okr.getId()));{
            repositoryOKr.deleteById(okr.getId());
        }
        return repositoryOKr.save(okr);
    }

    public Mono<Void> delete(String id) {
        return repositoryOKr.deleteById(id);
    }

    /*
    public Flux<OkrCollect> test() {
        return Assembler
                .generateOkrCollect(repositoryOKr.findAll(), repositoryKr.findAll());
    }*/

    public List<Kr> test (){
        return Assembler
                .generateKrs(repositoryKr.findAll(), "f3bb388a-eac0-4c44-9ba5-2a1710d0a358");
    }

}
