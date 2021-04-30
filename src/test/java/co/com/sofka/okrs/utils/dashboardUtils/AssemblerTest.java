package co.com.sofka.okrs.utils.dashboardUtils;

import co.com.sofka.okrs.TestUtils;
import co.com.sofka.okrs.dto.dashboard_dto.KrTable;
import co.com.sofka.okrs.dto.dashboard_dto.OkrList;
import co.com.sofka.okrs.dto.dashboard_dto.OkrTable;
import co.com.sofka.okrs.dto.dashboard_dto.UserView;
import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.List;

class AssemblerTest {

    @Test
    void generateUserView(){

        User user = new User("xxxx", "Daniel Alejandro", "danielburgos@example.com");
        UserView userViewExpected = new UserView("Daniel Alejandro", "danielburgos@example.com");

        UserView userViewActual = Assembler.generateUserView(user);

        Assertions.assertEquals(userViewExpected, userViewActual);
    }

    @Test
    void generateUserView_ErrorExpected(){
        User user = new User(null, null, null);

        Assertions.assertThrows(NullPointerException.class, ()-> {
            Assembler.generateUserView(user);
        });
    }

    @Test
    void generateOkrList(){

        Okr okr = new Okr("xxxx",
                "Ganancias Trimestrales",
                "Alcanzar xxx ganancias",
                "Daniel Burgos",
                "danielburgos@example.com",
                "xxxx",
                "Se desea alcanzar unas ganancias de xxx en el trimestre",
                "Desarrollo",
                0.7D,
                List.of());
        OkrList okrListExpected = new OkrList("xxxx", "Ganancias Trimestrales", 0.7D);

        OkrList okrListActual = Assembler.generateOkrList(okr);

        Assertions.assertEquals(okrListExpected, okrListActual);
    }

    @Test
    void generarOkrList_ErrorExpected(){
        Okr okr = new Okr(null,
                null,
                "Alcanzar xxx ganancias",
                "Daniel Burgos",
                "danielburgos@example.com",
                "xxxx",
                "Se desea alcanzar unas ganancias de xxx en el trimestre",
                "Desarrollo",
                0D,
                List.of());

        Assertions.assertThrows(NullPointerException.class, () -> {
            Assembler.generateOkrList(okr);
        });
    }

    @Test
    public void generarkrTabla(){
        Flux<Kr> krs = Flux.fromIterable(TestUtils.getListaKr());
        List<KrTable> krTableListaEsperada = TestUtils.getkrTablaListaEsperada();
        List<KrTable> krTableListaActual = Assembler.generatekrTable(krs);

        Assertions.assertEquals(krTableListaActual.get(0).getKeyResult(), krTableListaEsperada.get(0).getKeyResult());
        Assertions.assertEquals(krTableListaActual.get(0).getPersonInChargeNameKr(), krTableListaEsperada.get(0).getPersonInChargeNameKr());
        Assertions.assertEquals(krTableListaActual.get(0).getAdvanceKr(), krTableListaEsperada.get(0).getAdvanceKr());
        Assertions.assertEquals(krTableListaActual.get(1).getKeyResult(), krTableListaEsperada.get(1).getKeyResult());
        Assertions.assertEquals(krTableListaActual.get(1).getPersonInChargeNameKr(), krTableListaEsperada.get(1).getPersonInChargeNameKr());
        Assertions.assertEquals(krTableListaActual.get(1).getAdvanceKr(), krTableListaEsperada.get(1).getAdvanceKr());
        Assertions.assertEquals(krTableListaActual.get(2).getKeyResult(), krTableListaEsperada.get(2).getKeyResult());
        Assertions.assertEquals(krTableListaActual.get(2).getPersonInChargeNameKr(), krTableListaEsperada.get(2).getPersonInChargeNameKr());
        Assertions.assertEquals(krTableListaActual.get(2).getAdvanceKr(), krTableListaEsperada.get(2).getAdvanceKr());
    }

    @Test
    public void generarOkrTabla(){
        Mono<Okr> okr = TestUtils.getMonoOkr();
        Flux<Kr> krs = Flux.fromIterable(TestUtils.getListaKr());

        OkrTable okrTableEsperado = TestUtils.getokrTablaEsperado();

        StepVerifier.create(Assembler.generateOkrTable(okr, krs)).assertNext(okrTabla -> {
            Assertions.assertEquals(okrTabla.getTitle(), okrTableEsperado.getTitle());
            Assertions.assertEquals(okrTabla.getObjective(), okrTableEsperado.getObjective());
            Assertions.assertEquals(okrTabla.getPersonInChargeNameOkr(), okrTableEsperado.getPersonInChargeNameOkr());
            Assertions.assertEquals(okrTabla.getKeyResults(), okrTableEsperado.getKeyResults());
        }).verifyComplete();
    }
}