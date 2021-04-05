package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MedPlan  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "intake_intervals", nullable = false)
    private String intake_intervals;

    @Column(name = "start_date", nullable = false)
    private String start_date;

    @Column(name = "end_date", nullable = false)
    private String end_date;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH
            })
    @JoinTable(name = "medplan_has_medicine",
            joinColumns = { @JoinColumn(name = "medplan_id") },
            inverseJoinColumns = { @JoinColumn(name = "medication_id") })
    private List<Medication> meds = new ArrayList<Medication>();

    public MedPlan() {
    }

    public MedPlan(String intake_intervals, String start_date, String end_date, List<Medication> meds) {
        this.intake_intervals = intake_intervals;
        this.start_date = start_date;
        this.end_date = end_date;
        this.meds = meds;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIntake_intervals() { return intake_intervals; }

    public void setIntake_intervals(String intake_intervals) { this.intake_intervals = intake_intervals; }

    public String getStart_date() { return start_date; }

    public void setStart_date(String start_date) { this.start_date = start_date; }

    public String getEnd_date() { return end_date; }

    public void setEnd_date(String end_date) { this.end_date = end_date; }

    public List<Medication> getMeds() { return meds; }

    public void setMeds(List<Medication> meds) { this.meds = meds; }

    public void addMedication(Medication medication) { meds.add(medication); }
}
