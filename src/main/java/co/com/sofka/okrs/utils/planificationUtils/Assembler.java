package co.com.sofka.okrs.utils.planificationUtils;

import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.dto.dashboard_dto.KrTable;
import co.com.sofka.okrs.dto.dashboard_dto.OkrTable;
import co.com.sofka.okrs.dto.okrs_dto.OkrCollect;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Assembler {


    public static Flux<OkrCollect> generateOkrCollect(Flux<Okr> okr, Flux<Kr> krs){
        var okrCollect = new OkrCollect();

        return okr.flatMap(okrs -> {
            List<Kr> krsList = generateKrs(krs, okrs.getId());
            okrCollect.setId(okrs.getId());
            okrCollect.setTitle(okrs.getTitle());
            okrCollect.setAdvanceOkr(okrs.getAdvanceOkr());
            okrCollect.setKrs(krsList);
            return Flux.just(okrCollect);
        });
    }

    public static List<Kr> generateKrs(Flux<Kr> krs, String id) {
        List<Kr> krsByOkr = new ArrayList<>();
        krs.filter(kr -> kr.getOkrId().equals(id))
                .subscribe(kr -> krsByOkr.add(
                        new Kr(kr.getId(), kr.getOkrId(), kr.getKeyResult(), kr.getPersonInChargeNameKr(),
                                kr.getPersonInChargeEmailKr(), kr.getStartDate(), kr.getFinishDate(),
                                kr.getAdvanceKr(), kr.getPercentageWeight(), kr.getDescriptionKr())));
        return krsByOkr;
    }

}
