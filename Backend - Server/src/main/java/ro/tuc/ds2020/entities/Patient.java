package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Entity
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "medical_record")
    private String record;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private Person person;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<MedPlan> medPlans;

    public Patient() {
    }

    public Patient(String record, Person person) {
        this.record = record;
        this.person = person;
        this.medPlans = new ArrayList<MedPlan>();
    }

    public Patient(String record, Person person, List<MedPlan> medPlans) {
        this.record = record;
        this.person = person;
        this.medPlans = medPlans;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRecord() { return record; }

    public void setRecord(String record) { this.record = record; }

    public Person getPerson() { return person; }

    public void setPerson(Person person) { this.person = person; }

    public List<MedPlan> getMedPlans() { return medPlans; }

    public void setMedPlans(List<MedPlan> medPlans) { this.medPlans = medPlans; }

    public void addMedicationPlan(MedPlan medPlan) { this.medPlans.add(medPlan); }
}
