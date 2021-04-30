package co.com.sofka.okrs.controller.dashboardController;

import co.com.sofka.okrs.domain.User;
import co.com.sofka.okrs.dto.dashboard_dto.*;
import co.com.sofka.okrs.service.dashboardService.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
public class ControladorDashboard {
    @Autowired
    private DashboardService dashboardService;


    @GetMapping(value = "dashboard/user/{id}")
    public Mono<UserView> userById(@PathVariable("id") String id){
        return dashboardService.userById(Objects.requireNonNull(id));
    }

    @GetMapping(value = "dashboard/user-okrs/{id}")
    public Flux<OkrList> okrsByUser(@PathVariable("id") String id){
        return dashboardService.okrByUser(Objects.requireNonNull(id));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There is not element stored with that id")
    @ExceptionHandler({IllegalArgumentException.class})
    public void handleIllegalArgumentException(){
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NullPointerException.class})
    public void handleNullPointerException(){
    }

    @GetMapping(value = "dashboard/okrTable/{id}")
    public Mono<OkrTable> findOkrTablebyId(@PathVariable("id")String id){
        return dashboardService.findOkrTableById(Objects.requireNonNull(id));
    }

    @GetMapping(value = "dashboard/okrAdvance/{id}")
    public Mono<Double> findAdvanceOkrByOkrId(@PathVariable("id")String id){
        return  dashboardService.findAdvanceOkrByOkrId(Objects.requireNonNull(id));
    }

    @GetMapping(value = "dashboard/burndownchart/{id}")
    public Mono<OkrBurnDownChart> generateBurnDownChartDataByOkrId(@PathVariable("id") String id) {
        return dashboardService.generateBurnDownData(Objects.requireNonNull(id));
    }
    @GetMapping(value = "dashboard/krsAdvance/{id}")
    public Flux<PieKr> findAdvanceKrsByOkrId(@PathVariable("id")String id){
        return  dashboardService.findAdvanceKrsByOkrId(Objects.requireNonNull(id));
    }

    @GetMapping(value = "dashboard/barchart/{id}")
    public Mono<OkrBarChart> generateBarChartDataByOkrId(@PathVariable("id") String id) {
        return dashboardService.generateBarChartData(Objects.requireNonNull(id));
    }

}
