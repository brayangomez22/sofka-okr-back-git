package co.com.sofka.okrs.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document
public class Kr {

    @Id
    private String id;
    private String okrId;
    private String keyResult;
    private String personInChargeNameKr;
    private String personInChargeEmailKr;
    private Date startDate;
    private Date finishDate;
    private Double advanceKr;
    private Double percentageWeight;
    private String descriptionKr;

    public Kr(String id,
              String okrId,
              String keyResult,
              String personInChargeNameKr,
              String personInChargeEmailKr,
              Date startDate,
              Date finishDate,
              Double advanceKr,
              Double percentageWeight,
              String descriptionKr)
    {
        this.id = id;
        this.okrId = okrId;
        this.keyResult = keyResult;
        this.personInChargeNameKr = personInChargeNameKr;
        this.personInChargeEmailKr = personInChargeEmailKr;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.advanceKr = advanceKr;
        this.percentageWeight = percentageWeight;
        this.descriptionKr = descriptionKr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOkrId() {
        return okrId;
    }

    public void setOkrId(String okrId) {
        this.okrId = okrId;
    }

    public String getKeyResult() {
        return keyResult;
    }

    public void setKeyResult(String keyResult) {
        this.keyResult = keyResult;
    }

    public String getPersonInChargeNameKr() {
        return personInChargeNameKr;
    }

    public void setPersonInChargeNameKr(String personInChargeNameKr) {
        this.personInChargeNameKr = personInChargeNameKr;
    }

    public String getPersonInChargeEmailKr() {
        return personInChargeEmailKr;
    }

    public void setPersonInChargeEmailKr(String personInChargeEmailKr) {
        this.personInChargeEmailKr = personInChargeEmailKr;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Double getAdvanceKr() {
        return advanceKr;
    }

    public void setAdvanceKr(Double advanceKr) {
        this.advanceKr = advanceKr;
    }

    public Double getPercentageWeight() {
        return percentageWeight;
    }

    public void setPercentageWeight(Double percentageWeight) {
        this.percentageWeight = percentageWeight;
    }

    public String getDescriptionKr() {
        return descriptionKr;
    }

    public void setDescriptionKr(String descriptionKr) {
        this.descriptionKr = descriptionKr;
    }
}
