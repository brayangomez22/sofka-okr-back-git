package co.com.sofka.okrs.dto.dashboard_dto;

import java.util.Objects;

public class KrTable {

    private String keyResult;
    private String personInChargeNameKr;
    private Double advanceKr;

    public KrTable(String keyResult, String personInChargeNameKr, Double advanceKr) {
        this.keyResult = keyResult;
        this.personInChargeNameKr = personInChargeNameKr;
        this.advanceKr = advanceKr;
    }

    public String getKeyResult() {
        return keyResult;
    }


    public String getPersonInChargeNameKr() {
        return personInChargeNameKr;
    }



    public Double getAdvanceKr() {
        return advanceKr;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KrTable krTable = (KrTable) o;
        return Objects.equals(keyResult, krTable.keyResult) &&
                Objects.equals(personInChargeNameKr, krTable.personInChargeNameKr) &&
                Objects.equals(advanceKr, krTable.advanceKr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyResult, personInChargeNameKr, advanceKr);
    }
}
