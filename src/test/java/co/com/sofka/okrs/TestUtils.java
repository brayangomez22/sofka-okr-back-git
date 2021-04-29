package co.com.sofka.okrs;

import co.com.sofka.okrs.dto.dashboard_dto.KrTable;
import co.com.sofka.okrs.dto.dashboard_dto.OkrTable;
import co.com.sofka.okrs.domain.HistoricalAdvance;
import co.com.sofka.okrs.domain.Kr;
import co.com.sofka.okrs.domain.Okr;
import co.com.sofka.okrs.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestUtils {

    public static Mono<User> userFiltered(){
        return Mono.just(new User("xxxx", "Daniel Alejandro", "danielburgos@ejemplo.com"));
    }

    public static Flux<Okr> okrsByUser(){
        List<Okr> okrs = new ArrayList<>();

        Okr okr1 = new Okr("o-xxxxx1",
                "Ganancias Trimestrales",
                "Alcanzar xxx ganancias",
                "Daniel Burgos",
                "danielburgos@example.com",
                "xxxx",
                "Se desea alcanzar unas ganancias de xxx en el trimestre",
                "Desarrollo",
                0.7f,
                List.of());

        Okr okr2 = new Okr("o-xxxxx2",
                "Desarrollos Trimestriales",
                "Alcanzar xxx desarrollos completados",
                "Daniel Burgos",
                "danielburgos@example.com",
                "xxxx",
                "Se desea completar xxx desarrollos en el trimestre",
                "Desarrollo",
                0f,
                List.of());

        Okr okr3 = new Okr("o-xxxxx3",
                "Clientes Trimestriales",
                "Alcanzar xxx clientes nuevos",
                "Daniel Burgos",
                "danielburgos@example.com",
                "xxxx",
                "Se desea atraer xxx clientes nuevos",
                "Desarrollo",
                0.2f,
                List.of());

        okrs.add(okr1);
        okrs.add(okr3);
        okrs.add(okr2);

        return Flux.fromIterable(okrs);
    };

    public static Mono<Okr> getMonoOkr(){
        List<HistoricalAdvance> historical = new ArrayList<>();
        historical.add(new HistoricalAdvance( "01",0f));
        historical.add(new HistoricalAdvance("02",0.4f));
        historical.add(new HistoricalAdvance("03",0.68f));

        return Mono.just(new Okr(
                "6084801fb2ce1e4174af0245", "xxxxxx",
                "lkijytfvbnm", "danielBurgos",
                "daniel@gmail.com", "ertyuiokjhgf",
                "poiuytfdcvbnm,.uyt", "sofkaU",
                0.68f,historical
        ));
    }

    public static List<Kr> getListaKr(){
        List<Kr> listaKr = new ArrayList<>();
        listaKr.add(new Kr("60880fcdfdd7c89e44e4eaf6",
                "6084801fb2ce1e4174af0245", "Elaborar contenido campus",
                "Oscar Lopera", "oscar@gmail.com",
                new Date(), new Date(),
                0.5f, 0.3f, "Elaborar contenido campus sofka"));
        listaKr.add(new Kr("608810d15463112f24fd2593",
                "6084801fb2ce1e4174af0245", "Planear Retos Sideralis",
                "Oscar Lopera", "oscar@gmail.com",
                new Date(), new Date(),
                0.5f, 0.3f, "Planear Retos Sideralis"));
        listaKr.add(new Kr("6088116d5463112f24fd2594",
                "6084801fb2ce1e4174af0245", "Elaborar diapositivas Clases",
                "Oscar Lopera", "oscar@gmail.com",
                new Date(), new Date(),
                0.5f, 0.4f, "Elaborar diapositivas Clases"));
        return listaKr;
    }

    public static OkrTable getokrTablaEsperado(){
        return new OkrTable("xxxxxx", "lkijytfvbnm",
                "danielBurgos", getkrTablaListaEsperada());
    }
    public static List<KrTable> getkrTablaListaEsperada(){
        List<KrTable> krTableListaEsperada = new ArrayList<>();
        krTableListaEsperada.add(new KrTable("Elaborar contenido campus",
                "Oscar Lopera",0.5f));
        krTableListaEsperada.add(new KrTable("Planear Retos Sideralis",
                "Oscar Lopera",0.5f));
        krTableListaEsperada.add(new KrTable("Elaborar diapositivas Clases",
                "Oscar Lopera",0.5f));
        return krTableListaEsperada;
    }

}
