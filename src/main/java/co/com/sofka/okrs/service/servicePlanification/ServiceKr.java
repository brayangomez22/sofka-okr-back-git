package co.com.sofka.okrs.service.servicePlanification;

import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.repository.RepositoryKr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ServiceKr {

    @Autowired
    private RepositoryKr repositoryKr;



    public Flux<Kr> findAll() {
        return repositoryKr.findAll();
    }


    public Mono<Kr> save(Kr kr) {
        repositoryKr.findAll().filter(x -> x.getId() == kr.getId());
        {
            repositoryKr.deleteById(kr.getId());
            return repositoryKr.save(kr);
        }
    }


        public Mono<Void> delete (String id){
            return repositoryKr.deleteById(id);
        }
    }

