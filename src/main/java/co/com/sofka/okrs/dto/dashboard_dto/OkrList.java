package co.com.sofka.okrs.dto.dashboard_dto;

import java.util.Objects;

public class OkrList {

    private String id;
    private String title;
    private Double okrProgress;

    public OkrList(String id, String title, Double okrProgress) {
        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.okrProgress = Objects.requireNonNull(okrProgress);
    }

    public String getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public Double getOkrProgress() {
        return okrProgress;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OkrList okrList = (OkrList) o;
        return Double.compare(okrList.okrProgress, okrProgress) == 0 && Objects.equals(id, okrList.id) && Objects.equals(title, okrList.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, okrProgress);
    }
}
