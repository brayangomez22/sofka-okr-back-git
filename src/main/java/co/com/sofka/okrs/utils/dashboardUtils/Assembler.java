package co.com.sofka.okrs.utils.dashboardUtils;

import co.com.sofka.okrs.domain.HistoricalAdvance;
import co.com.sofka.okrs.dto.dashboard_dto.*;
import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Assembler {

    public static UserView generateUserView(User user){
        return new UserView(user.getName(), user.getEmail());
    }

    public static OkrList generateOkrList(Okr okr){
        return new OkrList(okr.getId(), okr.getTitle(), okr.getAdvanceOkr());
    }

    public static Mono<OkrTable> generateOkrTable(Mono<Okr> okr, Flux<Kr> krs){
        OkrTable okrTable = new OkrTable();
        List<KrTable> krTable = generatekrTable(krs);
        return okr.flatMap(okr1 ->{
            okrTable.setTitle(okr1.getTitle());
            okrTable.setObjective(okr1.getObjective());
            okrTable.setPersonInChargeNameOkr(okr1.getPersonInChargeNameOkr());
            okrTable.setKeyResults(krTable);
            return Mono.just(okrTable);
        });
    }

    public static List<KrTable> generatekrTable(Flux<Kr> krs){
        List<KrTable> krTable = new ArrayList<>();
        krs.subscribe(kr -> krTable.add(new KrTable(kr.getKeyResult(), kr.getPersonInChargeNameKr(), kr.getAdvanceKr())));
        return krTable;
    }

    public static OkrBurnDownChart generateBurnDownData(Okr okr, Kr startingDateKr, Kr finishingDateKr){

        List<String> labels = new ArrayList<>();
        List<Integer> expectedProgress = new ArrayList<>();
        List<Double> actualProgress = new ArrayList<>();
        LocalDate startingDate = convertDateToLocalDate(startingDateKr.getStartDate()).withDayOfMonth(28);
        List<HistoricalAdvance> historicalAdvance = okr.getHistoricalOkr();

        int okrDuration = calculateOkrDurationInMonths(startingDateKr.getStartDate(), finishingDateKr.getFinishDate());
        int historicalIndex = 0;
        Double tempPercentage = 0.0;
        int historicalAdvanceLength = historicalAdvance.size() - 1;


        for(int i = 0; hasNotOkrFinished(okrDuration, i); i++){
            LocalDate nextAdvanceDate = convertDateToLocalDate(historicalAdvance.get(historicalIndex).getDateUpdate());

            if(isAfterOrEquals(startingDate.plusMonths(i), nextAdvanceDate)){
                tempPercentage = historicalAdvance.get(historicalIndex).getNewAdvance();
                historicalIndex = isIndexOnBounds(historicalIndex, historicalAdvanceLength) ? historicalIndex +=1 : historicalIndex;
            }
            if(isBeforeOrEquals(startingDate.plusMonths(i - 1), nextAdvanceDate)){
                actualProgress.add(calculateActualProgress(tempPercentage));
            }
            labels.add(startingDate.plusMonths(i).format(DateTimeFormatter.ofPattern("MM-yy")));
            expectedProgress.add(calculateExpectedProgress(okrDuration, i));
        }

         return new OkrBurnDownChart(actualProgress, expectedProgress, labels);
    }

    public static OkrBarChart generateBarChartData(Okr okr, Kr startingDateKr, Kr finishingDateKr){

        List<String> labels = new ArrayList<>();
        List<Integer> expectedProgress = new ArrayList<>();
        List<Double> actualProgress = new ArrayList<>();
        LocalDate startingDate = convertDateToLocalDate(startingDateKr.getStartDate()).withDayOfMonth(28);
        List<HistoricalAdvance> historicalAdvance = okr.getHistoricalOkr();

        int okrDuration = calculateOkrDurationInMonths(startingDateKr.getStartDate(), finishingDateKr.getFinishDate());
        int historicalIndex = 0;
        Double tempPercentage = 0.0;
        int historicalAdvanceLength = historicalAdvance.size() - 1;


        for(int i = 0; hasNotOkrFinished(okrDuration, i); i++){
            LocalDate nextAdvanceDate = convertDateToLocalDate(historicalAdvance.get(historicalIndex).getDateUpdate());

            if(isAfterOrEquals(startingDate.plusMonths(i), nextAdvanceDate)){
                tempPercentage = historicalAdvance.get(historicalIndex).getNewAdvance();
                historicalIndex = isIndexOnBounds(historicalIndex, historicalAdvanceLength) ? historicalIndex +=1 : historicalIndex;
            }
            if(isBeforeOrEquals(startingDate.plusMonths(i - 1), nextAdvanceDate)){
                actualProgress.add(tempPercentage);
            }
            labels.add(startingDate.plusMonths(i).format(DateTimeFormatter.ofPattern("MM-yy")));
            expectedProgress.add(100);
        }

        return new OkrBarChart(actualProgress, expectedProgress, labels);
    }

    private static boolean isIndexOnBounds(int historicalIndex, int historicalAdvanceLength) {
        return historicalIndex < historicalAdvanceLength;
    }


    private static Double calculateActualProgress(Double tempPercentage) {
        return 100 - tempPercentage;
    }

    private static int calculateExpectedProgress(int okrDuration, int i) {
        return (( 100 - 100*i / okrDuration));
    }

    private static boolean hasNotOkrFinished(int okrDuration, int i) {
        return i <= okrDuration;
    }

    private static int calculateOkrDurationInMonths(Date finishDate, Date startingDate){

        LocalDate finish = convertDateToLocalDate(finishDate);
        LocalDate starting = convertDateToLocalDate(startingDate);
        Period periodOkr = Period.between(starting, finish);

        return Math.abs(periodOkr.getYears()) * 12 + Math.abs(periodOkr.getMonths());
    }

    private static LocalDate convertDateToLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.of("America/Bogota")).toLocalDate();
    }

    private static boolean isAfterOrEquals(LocalDate dateToCompare, LocalDate dateToCompareTo){
        return dateToCompare.isAfter(dateToCompareTo) || dateToCompare.isEqual(dateToCompareTo);
    }

    private static boolean isBeforeOrEquals(LocalDate dateToCompare, LocalDate dateToCompareTo){
        return dateToCompare.isBefore(dateToCompareTo) || dateToCompare.isEqual(dateToCompareTo);
    }
}
