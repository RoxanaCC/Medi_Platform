package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class SensorData implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "patientID", nullable = false)
    private String patientID;

    @Column(name = "start_time", nullable = false)
    private long start_time;

    @Column(name = "end_time", nullable = false)
    private long end_time;

    @Column(name = "activity", nullable = false)
    private String activity;

    @Column(name = "problem", nullable = false)
    private boolean problem;

    public SensorData() {
    }

    public SensorData(String patientID, long start_time, long end_time, String activity, boolean problem) {
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
}
