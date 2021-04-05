package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.tuc.ds2020.entities.Medication;

import java.util.List;
import java.util.UUID;

public interface MedicationRepository extends JpaRepository<Medication, UUID> {

    @Query(value = "SELECT m " +
            "FROM Medication m " +
            "ORDER BY m.id")
    List<Medication> getAllOrdered();

    List<Medication> findByName(String name);
}
