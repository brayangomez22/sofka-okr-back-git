package co.com.sofka.okrs.dto.okrs_dto;

import co.com.sofka.okrs.domain.Kr;

import java.util.List;
import java.util.Objects;

public class OkrCollect {
    private String id;
    private String title;
    private Float advanceOkr;
    private List<Kr> krs;

    public OkrCollect(String id, String title, Float advanceOkr, List<Kr> krs) {
        this.id = id;
        this.title = title;
        this.advanceOkr = advanceOkr;
        this.krs = krs;
    }

    public OkrCollect() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getAdvanceOkr() {
        return advanceOkr;
    }

    public void setAdvanceOkr(Float advanceOkr) {
        this.advanceOkr = advanceOkr;
    }

    public List<Kr> getKrs() {
        return krs;
    }

    public void setKrs(List<Kr> krs) {
        this.krs = krs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OkrCollect that = (OkrCollect) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(advanceOkr, that.advanceOkr) && Objects.equals(krs, that.krs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, advanceOkr, krs);
    }
}
