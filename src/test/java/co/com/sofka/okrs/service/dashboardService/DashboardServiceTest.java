package co.com.sofka.okrs.service.dashboardService;

import co.com.sofka.okrs.TestUtils;
import co.com.sofka.okrs.dto.dashboard_dto.OkrList;
import co.com.sofka.okrs.dto.dashboard_dto.UserView;
import co.com.sofka.okrs.repository.RepositoryKr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import co.com.sofka.okrs.repository.UserRepository;
import co.com.sofka.okrs.service.dashboardService.DashboardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @InjectMocks
    DashboardService dashboardService;

    @Mock
    UserRepository userRepository;

    @Mock
    RepositoryOkr repositoryOKR;

    @Mock
    RepositoryKr repositoryKr;

    @Test
    void userById(){

        when(userRepository.findById("xxxx")).thenReturn(TestUtils.userFiltered());

        StepVerifier.create(dashboardService.userById("xxxx"))
                .expectNext(new UserView("Daniel Alejandro", "danielburgos@ejemplo.com"))
                .verifyComplete();
    }

    @Test
    void userById_ErrorExpected(){

        when(userRepository.findById("xxxx")).thenReturn(Mono.empty());

        StepVerifier.create(dashboardService.userById("xxxx"))
                .expectError().verify();
    }

    @Test
    void okrByUser(){

        when(repositoryOKR.findByUserIdOrderByAdvanceOkrDesc("xxxx")).thenReturn(TestUtils.okrsByUser());

        StepVerifier.create(dashboardService.okrByUser("xxxx"))
                .expectNext(new OkrList("o-xxxxx1", "Ganancias Trimestrales", 0.7f))
                .expectNext(new OkrList("o-xxxxx3", "Clientes Trimestriales", 0.2f))
                .expectNext(new OkrList("o-xxxxx2", "Desarrollos Trimestriales", 0f))
                .verifyComplete();
    }

    @Test
    void okrByUser_ErrorExpected(){

        when(repositoryOKR.findByUserIdOrderByAdvanceOkrDesc("xxxx")).thenReturn(Flux.empty());

        StepVerifier.create(dashboardService.okrByUser("xxxx"))
                .expectError().verify();
    }

    @Test
    public void findOkrTableById(){
        when(repositoryOKR.findById("6084801fb2ce1e4174af0245")).thenReturn(TestUtils.getMonoOkr());

        when(repositoryKr.findByOkrId("6084801fb2ce1e4174af0245")).thenReturn(Flux.fromIterable(TestUtils.getListaKr()));

        StepVerifier.create(dashboardService.findOkrTableById("6084801fb2ce1e4174af0245")).
                expectNext(TestUtils.getokrTablaEsperado()).verifyComplete();
    }

    @Test
    public void findOkrTableByIdExpectedError(){
        when(repositoryOKR.findById("xxxx")).thenReturn(Mono.empty());
        when(repositoryKr.findByOkrId("xxxx")).thenReturn(Flux.empty());

        StepVerifier.create(dashboardService.findOkrTableById("xxxx"))
                .expectComplete().verify();
    }

    @Test
    public void findAdvanceOkrByOkrId(){
        when(repositoryOKR.findById("6084801fb2ce1e4174af0245")).thenReturn(TestUtils.getMonoOkr());
        StepVerifier.create(dashboardService.findAdvanceOkrByOkrId("6084801fb2ce1e4174af0245")).
                expectNext(68.0f).verifyComplete();
    }

    @Test
    public void findAdvanceOkrByOkrIdExpectedError(){
        when(repositoryOKR.findById("xxxx")).thenReturn(Mono.empty());

        StepVerifier.create(dashboardService.findAdvanceOkrByOkrId("xxxx"))
                .expectComplete().verify();
    }

}