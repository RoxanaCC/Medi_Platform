package ro.tuc.ds2020.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Medication  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "side_effects")
    private String side_effects;

    @Column(name = "dosage", nullable = false)
    private Integer dosage;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.DETACH
            },
            mappedBy = "meds")
    @JsonIgnore
    public List<MedPlan> medPlans = new ArrayList<MedPlan>();


    public Medication() {
    }

    public Medication(String name, String side_effects, Integer dosage) {
        this.name = name;
        this.side_effects = side_effects;
        this.dosage = dosage;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSide_effects() { return side_effects; }

    public void setSide_effects(String side_effects) { this.side_effects = side_effects; }

    public Integer getDosage() { return dosage; }

    public void setDosage(Integer dosage) { this.dosage = dosage; }
}
