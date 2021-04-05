package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;
import java.util.List;

public class MedPlanDetailsDTO extends RepresentationModel<MedPlanDetailsDTO>{

    private UUID id;
    private String intake_intervals;
    private String start_date;
    private String end_date;
    private String meds;

    public MedPlanDetailsDTO() {
    }

    public MedPlanDetailsDTO(UUID id, String intake_intervals, String start_date, String end_date, String meds) {
        this.id = id;
        this.intake_intervals = intake_intervals;
        this.start_date = start_date;
        this.end_date = end_date;
        this.meds = meds;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public String getIntake_intervals() { return intake_intervals; }

    public void setIntake_intervals(String intake_intervals) { this.intake_intervals = intake_intervals; }

    public String getStart_date() { return start_date; }

    public void setStart_date(String start_date) { this.start_date = start_date; }

    public String getEnd_date() { return end_date; }

    public void setEnd_date(String end_date) { this.end_date = end_date; }

    public String getMeds() { return meds; }

    public void setMeds(String meds) { this.meds = meds; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedPlanDetailsDTO that = (MedPlanDetailsDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(intake_intervals, that.intake_intervals) &&
                Objects.equals(start_date, that.start_date) &&
                Objects.equals(end_date, that.end_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, intake_intervals, start_date, end_date);
    }
}
