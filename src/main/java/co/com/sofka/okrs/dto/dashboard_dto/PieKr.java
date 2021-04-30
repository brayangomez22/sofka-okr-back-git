package co.com.sofka.okrs.dto.dashboard_dto;

import java.util.Objects;

public class PieKr {
    private String keyResult;
    private Double advanceKr;

    public PieKr() {
    }

    public PieKr(String keyResult, Double advanceKr) {
        this.keyResult = keyResult;
        this.advanceKr = advanceKr;
    }

    public String getKeyResult() {
        return keyResult;
    }

    public void setKeyResult(String keyResult) {
        this.keyResult = keyResult;
    }

    public Double getAdvanceKr() {
        return advanceKr;
    }

    public void setAdvanceKr(Double advanceKr) {
        this.advanceKr = advanceKr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieKr pieKr = (PieKr) o;
        return Objects.equals(keyResult, pieKr.keyResult) &&
                Objects.equals(advanceKr, pieKr.advanceKr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyResult, advanceKr);
    }
}
