package co.com.sofka.okrs.dto.dashboard_dto;

import java.util.List;
import java.util.Objects;

public class OkrBurnDownChart {

    private List<Double> actualPercentage;
    private List<Integer> expectedPercentage;
    private List<String> labels;

    public OkrBurnDownChart(List<Double> actualPercentage, List<Integer> expectedPercentage, List<String> labels) {
        this.actualPercentage = actualPercentage;
        this.expectedPercentage = expectedPercentage;
        this.labels = labels;
    }

    public List<Double> getActualPercentage() {
        return actualPercentage;
    }


    public List<Integer> getExpectedPercentage() {
        return expectedPercentage;
    }


    public List<String> getLabels() {
        return labels;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OkrBurnDownChart chart = (OkrBurnDownChart) o;
        return Objects.equals(actualPercentage, chart.actualPercentage) && Objects.equals(expectedPercentage, chart.expectedPercentage) && Objects.equals(labels, chart.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actualPercentage, expectedPercentage, labels);
    }

}
