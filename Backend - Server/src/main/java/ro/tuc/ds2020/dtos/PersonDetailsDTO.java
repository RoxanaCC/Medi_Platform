package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class PersonDetailsDTO extends RepresentationModel<PersonDetailsDTO> {

    private UUID id;
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String name;
    private String birthdate;
    private String address;
    private String gender;
    private String role;


    public PersonDetailsDTO() {
    }

    public PersonDetailsDTO(String username, String password, String name, String birthdate, String address, String gender, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.gender = gender;
        this.role = role;
    }

    public PersonDetailsDTO(UUID id, String username, String password, String name, String birthdate, String address, String gender, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.gender = gender;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() { return birthdate; }

    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDetailsDTO that = (PersonDetailsDTO) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(name, that.name) &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, name, birthdate, address);
    }
}
