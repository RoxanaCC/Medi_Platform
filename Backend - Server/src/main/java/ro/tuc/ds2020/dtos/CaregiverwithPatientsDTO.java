package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.Patient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CaregiverwithPatientsDTO extends RepresentationModel<CaregiverwithPatientsDTO>{

    private UUID id;
    private UUID fr_id;
    private String username;
    private String password;
    private String name;
    private String birthdate;
    private String address;
    private String gender;
    private List<PatientDetailsDTO> patients;

    public CaregiverwithPatientsDTO() {
    }

    public CaregiverwithPatientsDTO(UUID id, UUID fr_id, String username, String password, String name, String birthdate, String address, String gender, List<PatientDetailsDTO> patients) {
        this.id = id;
        this.fr_id = fr_id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        this.gender = gender;
        this.patients = patients;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFr_id() { return fr_id; }

    public void setFr_id(UUID fr_id) { this.fr_id = fr_id; }

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

    public List<PatientDetailsDTO> getPatients() { return patients; }

    public void setPatients(List<PatientDetailsDTO> patients) { this.patients = patients; }

    public void addPatient(PatientDetailsDTO p) { patients.add(p); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaregiverwithPatientsDTO that = (CaregiverwithPatientsDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fr_id, that.fr_id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate, address, gender);
    }
}
