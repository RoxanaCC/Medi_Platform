package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.Caregiver;

import java.util.List;
import java.util.UUID;

public interface CaregiverRepository extends JpaRepository<Caregiver, UUID>{

    @Query(value = "SELECT c FROM Caregiver c " +
            "INNER JOIN Person p ON c.person = p.id " +
            "ORDER BY p.name")
    List<Caregiver> getAllOrdered();

}
