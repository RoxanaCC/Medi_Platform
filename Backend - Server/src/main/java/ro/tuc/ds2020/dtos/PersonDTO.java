package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;

public class PersonDTO extends RepresentationModel<PersonDTO> {
    private UUID id;
    private String name;
    private String birthdate;
    private String role;

    public PersonDTO() {
    }

    public PersonDTO(UUID id, String name, String birthdate, String role) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() { return birthdate; }

    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }

    public String getRole() {
        return role;
    }

    public void setARole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO that = (PersonDTO) o;
        return Objects.equals(name, that.name)&&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthdate, role);
    }
}
