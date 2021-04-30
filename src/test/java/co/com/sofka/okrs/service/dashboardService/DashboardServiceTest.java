package co.com.sofka.okrs.service.dashboardService;

import co.com.sofka.okrs.TestUtils;
import co.com.sofka.okrs.dto.dashboard_dto.*;
import co.com.sofka.okrs.repository.RepositoryKr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import co.com.sofka.okrs.repository.UserRepository;
import co.com.sofka.okrs.testHelpers.dashboardTestHelpers.TestHelpersDashboard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

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
                .expectNext(new OkrList("o-xxxxx1", "Ganancias Trimestrales", 70.0))
                .expectNext(new OkrList("o-xxxxx3", "Clientes Trimestriales", 20.0))
                .expectNext(new OkrList("o-xxxxx2", "Desarrollos Trimestriales", 0.0))
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
                expectNext(68.0).verifyComplete();
    }

    @Test
    public void findAdvanceOkrByOkrIdExpectedError(){
        when(repositoryOKR.findById("xxxx")).thenReturn(Mono.empty());

        StepVerifier.create(dashboardService.findAdvanceOkrByOkrId("xxxx"))
                .expectComplete().verify();
    }

    @Test
    public void generateBurnDownData(){

        String okrId = TestHelpersDashboard.generate_okr().getId();
        List<Double> expectedActualPercentage = List.of(100.0, 83.0, 83.0, 80.0, 80.0, 80.0, 80.0, 60.0);
        List<Integer> expectedExpectedPercentage = List.of(100, 92, 84, 75, 67, 59, 50, 42, 34, 25, 17, 9, 0);
        List<String> expectedLabel = List.of("01-20", "02-20", "03-20", "04-20","05-20", "06-20", "07-20", "08-20", "09-20", "10-20", "11-20", "12-20", "01-21");
        OkrBurnDownChart chart = new OkrBurnDownChart(expectedActualPercentage, expectedExpectedPercentage, expectedLabel);

        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr3()));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr1()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_okr()));

        StepVerifier.create(dashboardService.generateBurnDownData(okrId)).expectNext(chart).verifyComplete();
    }

    @Test
    public void generateBurnDownData_errorExpected_NoOkrsRelated(){

        String okrId = TestHelpersDashboard.generate_okr().getId();

        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr3()));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr1()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.error(new NullPointerException("There is not Okrs related to that Id")));

        StepVerifier.create(dashboardService.generateBurnDownData(okrId)).expectError().verify();
    }

    @Test
    public void generateBurnDownData_errorExpected_NoKrsRelated(){

        String okrId = TestHelpersDashboard.generate_okr().getId();

        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.error(new NullPointerException("There is not Okrs related to that Id")));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr1()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_okr()));

        StepVerifier.create(dashboardService.generateBurnDownData(okrId)).expectError().verify();
    }

    @Test
    public void findAdvanceKrsByOkrId(){
        when(repositoryKr.findByOkrId("6084801fb2ce1e4174af0245")).thenReturn(TestUtils.getFluxKr());
        StepVerifier.create(dashboardService.findAdvanceKrsByOkrId("6084801fb2ce1e4174af0245")).
                expectNext(new PieKr("Alcanzar xxx ganancias", 24.0 ))
                .expectNext(new PieKr("Alcanzar xxx desarrollos completados", 24.0 ))
                .expectNext(new PieKr("Alcanzar xxx clientes nuevos", 32.0 )).verifyComplete();
    }

    @Test
    public void findAdvanceKrsByOkrIdExpectedError(){
        when(repositoryOKR.findById("xxxx")).thenReturn(Mono.empty());

        StepVerifier.create(dashboardService.findAdvanceOkrByOkrId("xxxx"))
                .expectComplete().verify();
    }

    @Test
    public void generateBarChartData_errorExpected_NoOkrsRelated(){

        String okrId = TestHelpersDashboard.generate_okr().getId();

        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr3()));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr1()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.error(new NullPointerException("There is not Okrs related to that Id")));

        StepVerifier.create(dashboardService.generateBarChartData(okrId)).expectError().verify();
    }

    @Test
    public void generateBarChartData_errorExpected_NoKrsRelated(){

        String okrId = TestHelpersDashboard.generate_okr().getId();

        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.error(new NullPointerException("There is not Okrs related to that Id")));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr1()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_okr()));

        StepVerifier.create(dashboardService.generateBurnDownData(okrId)).expectError().verify();
    }

    @Test
    public void generateBarChartData(){

        String okrId = TestHelpersDashboard.generate_okr().getId();
        List<Double> expectedActualPercentage = List.of(0.0, 17.0, 17.0, 20.0, 20.0, 20.0, 20.0, 40.0);
        List<Integer> expectedExpectedPercentage = List.of(100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100);
        List<String> expectedLabel = List.of("01-20", "02-20", "03-20", "04-20","05-20", "06-20", "07-20", "08-20", "09-20", "10-20", "11-20", "12-20", "01-21");
        OkrBarChart chart = new OkrBarChart(expectedActualPercentage, expectedExpectedPercentage, expectedLabel);

        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr3()));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr1()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_okr()));

        StepVerifier.create(dashboardService.generateBarChartData(okrId)).expectNext(chart).verifyComplete();
    }

}