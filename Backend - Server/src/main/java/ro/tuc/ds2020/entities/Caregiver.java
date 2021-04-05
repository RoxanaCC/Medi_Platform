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
public class Caregiver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private Person person;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH)
    private List<Patient> patients;

    public Caregiver() {
    }

    public Caregiver(Person person) {
        this.person = person;
        this.patients = new ArrayList<Patient>();
    }

    public Caregiver(Person person, List<Patient> patients) {
        this.person = person;
        this.patients = patients;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Person getPerson() { return person; }

    public void setPerson(Person person) { this.person = person; }

    public List<Patient> getPatients() { return patients; }

    public void setPatients(List<Patient> patients) { this.patients = patients; }

    public void addPatient(Patient p) { this.patients.add(p); }
}
