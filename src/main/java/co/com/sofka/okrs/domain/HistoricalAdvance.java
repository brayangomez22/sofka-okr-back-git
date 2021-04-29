package co.com.sofka.okrs.domain;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class HistoricalAdvance {

    @Id
    private String id;
    private Date dateUpdate;
    private Float newAdvance;



    public HistoricalAdvance(String id,Float newAdvance) {
        this.id = id;
        this.dateUpdate = new Date();
        this.newAdvance = newAdvance;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public Float getNewAdvance() {
        return newAdvance;
    }

    public void setNewAdvance(Float newAdvance) {
        this.newAdvance = newAdvance;
    }

    public String getId() {
        return id;
    }
}
