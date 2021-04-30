package co.com.sofka.okrs.domain;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class HistoricalAdvance {

    @Id
    private String id;
    private Date dateUpdate;
    private Double newAdvance;

    
    public HistoricalAdvance(String id,Double newAdvance) {
        this.id = id;
        this.dateUpdate = new Date();
        this.newAdvance = newAdvance;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public Double getNewAdvance() {
        return newAdvance;
    }

    public void setNewAdvance(Double newAdvance) {
        this.newAdvance = newAdvance;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getId() {
        return id;
    }
}
