package co.com.sofka.okrs.service.dashboardService;

import co.com.sofka.okrs.dto.dashboard_dto.OkrList;
import co.com.sofka.okrs.dto.dashboard_dto.OkrTable;
import co.com.sofka.okrs.dto.dashboard_dto.UserView;
import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.repository.RepositoryKr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import co.com.sofka.okrs.repository.UserRepository;
import co.com.sofka.okrs.utils.dashboardUtils.Assembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RepositoryOkr repositoryOKR;

    @Autowired
    private RepositoryKr repositoryKr;

    public Mono<UserView> userById(String id){
        return userRepository.findById(id).map(Assembler::generateUserView)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found")));
    }

    public Flux<OkrList> okrByUser(String id){
        return repositoryOKR.findByUserIdOrderByAdvanceOkrDesc(id).map(Assembler::generateOkrList)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("There are not OKRs related with that User")));
    }

    public Mono<OkrTable> findOkrTableById(String id) {
        return Assembler
                .generateOkrTable(repositoryOKR.findById(id), repositoryKr.findByOkrId(id))
                .onErrorResume(e->Mono.error(new IllegalArgumentException("El okr no se encuentra registrado")));
    }

    public Mono<Float> findAdvanceOkrByOkrId(String id){
        return repositoryOKR.findById(id)
                .map(okr -> {
                    okr.setAdvanceOkr(okr.getAdvanceOkr()*100);
                    return okr.getAdvanceOkr();
                }).onErrorResume(e ->Mono.error(new IllegalArgumentException("El okr no se encuentra registrado")));
    }
}
