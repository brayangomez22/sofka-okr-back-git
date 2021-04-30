package co.com.sofka.okrs.service.dashboardService;

import co.com.sofka.okrs.dto.dashboard_dto.*;
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

    public Mono<Double> findAdvanceOkrByOkrId(String id){
        return repositoryOKR.findById(id)
                .map(Okr::getAdvanceOkr).onErrorResume(e ->Mono.error(new IllegalArgumentException("El okr no se encuentra registrado")));
    }

    public Flux<PieKr> findAdvanceKrsByOkrId(String id){
        PieKr pieKr = new PieKr();
        return  repositoryKr.findByOkrId(id).map(kr -> {
            pieKr.setAdvanceKr((kr.getAdvanceKr()*kr.getPercentageWeight())/100);
            pieKr.setKeyResult(kr.getKeyResult());
            return pieKr;}).onErrorResume(e ->Mono.error(new IllegalArgumentException("El okr no se encuentra registrado")));
    }

    public Mono<OkrBurnDownChart> generateBurnDownData(String id){
        return Mono.zip(repositoryOKR.findById(id), repositoryKr.findFirstByOkrIdOrderByFinishDate(id), repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(id))
                .map(element -> Assembler.generateBurnDownData(element.getT1(), element.getT2(), element.getT3()))
                .onErrorResume(e -> Mono.error(new IllegalArgumentException("There are no Okrs or Krs registered related to that ID")));
    }

    public Mono<OkrBarChart> generateBarChartData(String id){
        return Mono.zip(repositoryOKR.findById(id), repositoryKr.findFirstByOkrIdOrderByFinishDate(id), repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(id))
                .map(element -> Assembler.generateBarChartData(element.getT1(), element.getT2(), element.getT3()))
                .onErrorResume(e -> Mono.error(new IllegalArgumentException("There are no Okrs or Krs registered related to that ID")));
    }

}
