package co.com.sofka.okrs.testHelpers.dashboardTestHelpers;

import co.com.sofka.okrs.domain.HistoricalAdvance;
import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.domain.Okr;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class TestHelpersDashboard {

    public static List<HistoricalAdvance> generateDates(){
        HistoricalAdvance advance1 = new HistoricalAdvance("xxx", 0.0);
        advance1.setDateUpdate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        HistoricalAdvance advance2 = new HistoricalAdvance("xxx", 17.0);
        advance2.setDateUpdate(new GregorianCalendar(2020, Calendar.FEBRUARY, 2).getTime());
        HistoricalAdvance advance3 = new HistoricalAdvance("xxx", 20.0);
        advance3.setDateUpdate(new GregorianCalendar(2020, Calendar.APRIL, 1).getTime());
        HistoricalAdvance advance4 = new HistoricalAdvance("xxx", 40.0);
        advance4.setDateUpdate(new GregorianCalendar(2020, Calendar.AUGUST, 1).getTime());

        return List.of(advance1, advance2, advance3, advance4);
    }

    public static Okr generate_okr(){
        return  new Okr("xxx",
                "Prueba 1",
                "Que se ejecute la prueba",
                "Daniel Burgos",
                "danielburgos@ejemplo.com",
                "12",
                "Se espera que se ejecute la prueba",
                "Desarrollo",
                80.0,
                generateDates());
    }

    public static Kr generate_kr1(){
        return new Kr("xxx-1",
                "Kr Prueba 1",
                "Xxxx",
                "Daniel Burgos",
                "danielburgos@ejemplo.com",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JULY, 1).getTime(),
                20.0,
                40.0,
                "xxxxxx-description"
        );
    }

    public static Kr generate_kr2(){
        return new Kr("xxx-2",
                "Kr Prueba 2",
                "Xxxx",
                "Daniel Burgos",
                "danielburgos@ejemplo.com",
                new GregorianCalendar(2020, Calendar.FEBRUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.AUGUST, 1).getTime(),
                20.0,
                40.0,
                "xxxxxx-description"
        );
    }

    public static Kr generate_kr3(){
        return new Kr("xxx-3",
                "Kr Prueba 3",
                "Xxxx",
                "Daniel Burgos",
                "danielburgos@ejemplo.com",
                new GregorianCalendar(2020, Calendar.MARCH, 1).getTime(),
                new GregorianCalendar(2021, Calendar.JANUARY, 1).getTime(),
                20.0,
                20.0,
                "xxxxxx-description"
        );
    }
}
