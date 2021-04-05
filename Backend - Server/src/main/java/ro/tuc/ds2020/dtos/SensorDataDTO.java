package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import java.util.Objects;
import java.util.UUID;

public class SensorDataDTO  extends RepresentationModel<SensorDataDTO> {

    private UUID id;
    private String patientID;
    private long start_time;
    private long end_time;
    private String activity;
    private boolean problem;

    public SensorDataDTO() {
    }

    public SensorDataDTO(String patientID, long start_time, long end_time, String activity) {
        this.patientID = patientID;
        this.start_time = start_time;
        this.end_time = end_time;
        this.activity = activity;
    }

    public SensorDataDTO(UUID id, String patientID, long start_time, long end_time, String activity, boolean problem) {
        this.id = id;
        this.patientID = patientID;
        this.start_time = start_time;
        this.end_time = end_time;
        this.activity = activity;
        this.problem = problem;
    }


    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public String getPatientID() { return patientID; }

    public void setPatientID(String patientID) { this.patientID = patientID; }

    public long getStart_time() { return start_time; }

    public void setStart_time(long start_time) { this.start_time = start_time; }

    public long getEnd_time() { return end_time; }

    public void setEnd_time(long end_time) { this.end_time = end_time; }

    public String getActivity() { return activity; }

    public void setActivity(String activity) { this.activity = activity; }

    public boolean isProblem() { return problem; }

    public void setProblem(boolean problem) { this.problem = problem; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorDataDTO that = (SensorDataDTO) o;
        return Objects.equals(patientID, that.patientID)&&
                Objects.equals(start_time, that.start_time) &&
                Objects.equals(end_time, that.end_time) &&
                Objects.equals(activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientID, start_time, end_time, activity);
    }

}
