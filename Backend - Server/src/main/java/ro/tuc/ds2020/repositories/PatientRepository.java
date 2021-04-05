package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Patient;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID>{

    @Query(value = "SELECT d FROM Patient d " +
            "INNER JOIN Person p ON d.person = p.id " +
            "ORDER BY p.name")
    List<Patient> getAllOrdered();

}
