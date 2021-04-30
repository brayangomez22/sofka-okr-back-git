package co.com.sofka.okrs.controller.dashboardController;

import co.com.sofka.okrs.TestUtils;
import co.com.sofka.okrs.controller.dashboardController.ControladorDashboard;
import co.com.sofka.okrs.dto.dashboard_dto.OkrBarChart;
import co.com.sofka.okrs.dto.dashboard_dto.OkrBurnDownChart;
import co.com.sofka.okrs.dto.dashboard_dto.OkrList;
import co.com.sofka.okrs.dto.dashboard_dto.PieKr;
import co.com.sofka.okrs.repository.RepositoryKr;
import co.com.sofka.okrs.repository.RepositoryOkr;
import co.com.sofka.okrs.repository.UserRepository;
import co.com.sofka.okrs.service.dashboardService.DashboardService;
import co.com.sofka.okrs.testHelpers.dashboardTestHelpers.TestHelpersDashboard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ControladorDashboard.class)
@Import(DashboardService.class)
class ControladorDashboardTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RepositoryOkr repositoryOKR;

    @MockBean
    private RepositoryKr repositoryKr;

    @Test
    void userById(){

        when(userRepository.findById("xxxx")).thenReturn(TestUtils.userFiltered());

        webTestClient.get().uri("/dashboard/user/{id}", "xxxx")
                .exchange().expectStatus().isOk().expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Daniel Alejandro")
                .jsonPath("$.email").isNotEmpty()
                .jsonPath("$.email").isEqualTo("danielburgos@ejemplo.com");

        Mockito.verify(userRepository, times(1)).findById("xxxx");
    }

    @Test
    void userById_NoIdOnDataBase(){

        when(userRepository.findById("xxxx")).thenReturn(Mono.empty());

        webTestClient.get().uri("/dashboard/user/{id}", "xxxx")
                .exchange().expectStatus().isEqualTo(404);
    }

    @Test
    void userById_NullId(){
        webTestClient.get().uri("/dashboard/user/{id}", (Object) null)
                .exchange().expectStatus().isEqualTo(404);
    }


    @Test
    void okrByUser(){

        when(repositoryOKR.findByUserIdOrderByAdvanceOkrDesc("xxxx")).thenReturn(TestUtils.okrsByUser());

        Flux<OkrList> okrListaFlux =  webTestClient.get().uri("/dashboard/user-okrs/{id}", "xxxx")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk().returnResult(OkrList.class).getResponseBody();

        StepVerifier.create(okrListaFlux).expectNextCount(3).verifyComplete();

        Mockito.verify(repositoryOKR, times(1)).findByUserIdOrderByAdvanceOkrDesc("xxxx");
    }

    @Test
    void okrByUser_NullId(){
        webTestClient.get().uri("/dashboard/user-okrs/{id}", (Object) null)
                .exchange().expectStatus().isEqualTo(404);
    }

    @Test
    void okrByUser_NoIdInDataBase(){
        when(repositoryOKR.findByUserIdOrderByAdvanceOkrDesc("xxxx")).thenReturn(Flux.empty());

        webTestClient.get().uri("/dashboard/user-okrs/{id}", "xxxx")
                .exchange().expectStatus().isEqualTo(404);
    }

    @Test
    void nullPointerException_controlled(){
        when(repositoryOKR.findByUserIdOrderByAdvanceOkrDesc("xxxx")).thenThrow(new NullPointerException());

        webTestClient.get().uri("/dashboard/user-okrs/{id}", "xxxx")
                .exchange().expectStatus().isEqualTo(400);
    }

    @Test
    void illegalArgumentException_controlled(){
        when(repositoryOKR.findByUserIdOrderByAdvanceOkrDesc("xxxx")).thenThrow(new IllegalArgumentException());

        webTestClient.get().uri("/dashboard/user-okrs/{id}", "xxxx")
                .exchange().expectStatus().isEqualTo(404);
    }

    @Test
    void findOkrTablebyId(){
        when(repositoryOKR.findById("6084801fb2ce1e4174af0245")).thenReturn(TestUtils.getMonoOkr());

        when(repositoryKr.findByOkrId("6084801fb2ce1e4174af0245")).thenReturn(Flux.fromIterable(TestUtils.getListaKr()));

        webTestClient.get().uri("/dashboard/okrTable/{id}", "6084801fb2ce1e4174af0245")
                .exchange().expectStatus().isOk().expectBody()
                .jsonPath("$.title").isNotEmpty()
                .jsonPath("$.title").isEqualTo("xxxxxx")
                .jsonPath("$.personInChargeNameOkr").isNotEmpty()
                .jsonPath("$.personInChargeNameOkr").isEqualTo("danielBurgos");
    }

    @Test
    void findOkrTablebyIdWithNotFoundId(){
        when(repositoryOKR.findById("xxxx")).thenReturn(Mono.empty());
        when(repositoryKr.findByOkrId("xxxx")).thenReturn(Flux.empty());

        webTestClient.get().uri("/dashboard/okrTable/{id}", "xxxx")
                .exchange().expectStatus().isEqualTo(200);
    }

    @Test
    public void findAdvanceOkrByOkrId(){
        when(repositoryOKR.findById("6084801fb2ce1e4174af0245")).thenReturn(TestUtils.getMonoOkr());

        webTestClient.get().uri("/dashboard/okrAdvance/{id}", "6084801fb2ce1e4174af0245")
                .exchange().expectStatus().isOk().expectBody()
                .equals(68);

        Mockito.verify(repositoryOKR, times(1)).findById("6084801fb2ce1e4174af0245");
    }

    @Test
    public void findAdvanceOkrByOkrIdWithNotFoundOId(){
        when(repositoryOKR.findById("xxxx")).thenReturn(Mono.empty());

        webTestClient.get().uri("/dashboard/okrAdvance/{id}", "xxxx")
                .exchange().expectStatus().isEqualTo(200);
    }

    @Test
    void generateBurnDownChart(){

        String okrId = TestHelpersDashboard.generate_okr().getId();

        List<Double> expectedActualPercentage = List.of(100.0, 83.0, 83.0, 80.0, 80.0, 80.0, 80.0, 60.0);
        List<Integer> expectedExpectedPercentage = List.of(100, 92, 84, 75, 67, 59, 50, 42, 34, 25, 17, 9, 0);
        List<String> expectedLabel = List.of("01-20", "02-20", "03-20", "04-20","05-20", "06-20", "07-20", "08-20", "09-20", "10-20", "11-20", "12-20", "01-21");

        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr3()));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr1()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_okr()));

        webTestClient.get().uri("/dashboard/burndownchart/{id}", okrId)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk()
                .expectBody(OkrBurnDownChart.class)
                .consumeWith((okrBurnDownChartEntityExchangeResult) -> {
                    OkrBurnDownChart okrBurnDownChart = okrBurnDownChartEntityExchangeResult.getResponseBody();
                    Assertions.assertEquals(okrBurnDownChart.getExpectedPercentage(), expectedExpectedPercentage);
                    Assertions.assertEquals(okrBurnDownChart.getLabels(), expectedLabel);
                    Assertions.assertEquals(okrBurnDownChart.getActualPercentage(), expectedActualPercentage);
                });

        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDateDesc(okrId);
        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDate(okrId);
        Mockito.verify(repositoryOKR, times(1)).findById(okrId);
    }

    @Test
    void generateBurnDownChart_ErrorExpected_NoOkrRelatedToId(){

        String okrId = TestHelpersDashboard.generate_okr().getId();

        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr3()));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr1()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.error(new NullPointerException()));

        webTestClient.get().uri("/dashboard/burndownchart/{id}", okrId)
                .exchange().expectStatus().isEqualTo(404);

        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDateDesc(okrId);
        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDate(okrId);
        Mockito.verify(repositoryOKR, times(1)).findById(okrId);
    }

    @Test
    void generateBurnDownChart_ErrorExpected_NoKrRelatedToId() {

        String okrId = TestHelpersDashboard.generate_okr().getId();

        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.error(new NullPointerException()));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.error(new NullPointerException()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_okr()));


        webTestClient.get().uri("/dashboard/burndownchart/{id}", okrId)
                .exchange().expectStatus().isEqualTo(404);

        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDateDesc(okrId);
        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDate(okrId);
        Mockito.verify(repositoryOKR, times(1)).findById(okrId);
    }

    @Test
    public void findAdvanceKrsByOkrId(){
        when(repositoryKr.findByOkrId("6084801fb2ce1e4174af0245")).thenReturn(TestUtils.getFluxKr());

        Flux<PieKr> pieKrFlux = webTestClient.get().uri("/dashboard/krsAdvance/{id}", "6084801fb2ce1e4174af0245")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk().returnResult(PieKr.class).getResponseBody();

        StepVerifier.create(pieKrFlux).expectNextCount(3).verifyComplete();

        Mockito.verify(repositoryKr, times(1)).findByOkrId("6084801fb2ce1e4174af0245");
    }

    @Test
    public void findAdvanceKrsByOkrIdWithNotFoundOId(){
        when(repositoryKr.findByOkrId("6084801fb2ce1e4174af0245")).thenReturn(TestUtils.getFluxKr());

        webTestClient.get().uri("/dashboard/krsAdvance/{id}", "xxxx")
                .exchange().expectStatus().isEqualTo(400);
    }

    @Test
    void generateBarChart(){

        String okrId = TestHelpersDashboard.generate_okr().getId();

        List<Double> expectedActualPercentage = List.of(0.0, 17.0, 17.0, 20.0, 20.0, 20.0, 20.0, 40.0);
        List<Integer> expectedExpectedPercentage = List.of(100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100);
        List<String> expectedLabel = List.of("01-20", "02-20", "03-20", "04-20","05-20", "06-20", "07-20", "08-20", "09-20", "10-20", "11-20", "12-20", "01-21");


        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr3()));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr1()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_okr()));

        webTestClient.get().uri("/dashboard/barchart/{id}", okrId)
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .isOk()
                .expectBody(OkrBarChart.class)
                .consumeWith((okrBurnDownChartActual) -> {
                    OkrBarChart okrBarChart = okrBurnDownChartActual.getResponseBody();
                    Assertions.assertEquals(okrBarChart.getExpectedPercentage(), expectedExpectedPercentage);
                    Assertions.assertEquals(okrBarChart.getLabels(), expectedLabel);
                    Assertions.assertEquals(okrBarChart.getActualPercentage(), expectedActualPercentage);
                });

        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDateDesc(okrId);
        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDate(okrId);
        Mockito.verify(repositoryOKR, times(1)).findById(okrId);
    }

    @Test
    void generateBarChart_ErrorExpected_NoOkrRelatedToId(){

        String okrId = TestHelpersDashboard.generate_okr().getId();

        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr3()));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_kr1()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.error(new NullPointerException()));

        webTestClient.get().uri("/dashboard/barchart/{id}", okrId)
                .exchange().expectStatus().isEqualTo(404);

        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDateDesc(okrId);
        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDate(okrId);
        Mockito.verify(repositoryOKR, times(1)).findById(okrId);
    }

    @Test
    void generateBarChart_ErrorExpected_NoKrRelatedToId() {

        String okrId = TestHelpersDashboard.generate_okr().getId();

        when(repositoryKr.findFirstByOkrIdOrderByFinishDateDesc(okrId)).thenReturn(Mono.error(new NullPointerException()));
        when(repositoryKr.findFirstByOkrIdOrderByFinishDate(okrId)).thenReturn(Mono.error(new NullPointerException()));
        when(repositoryOKR.findById(okrId)).thenReturn(Mono.just(TestHelpersDashboard.generate_okr()));


        webTestClient.get().uri("/dashboard/barchart/{id}", okrId)
                .exchange().expectStatus().isEqualTo(404);

        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDateDesc(okrId);
        Mockito.verify(repositoryKr, times(1)).findFirstByOkrIdOrderByFinishDate(okrId);
        Mockito.verify(repositoryOKR, times(1)).findById(okrId);
    }
}