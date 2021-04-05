package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Intakes;

import java.util.List;
import java.util.UUID;

@Repository
public interface IntakesRepository extends JpaRepository<Intakes, UUID> {
    @Query(value = "SELECT i " +
            "FROM Intakes i " +
            "ORDER BY i.id")
    List<Intakes> getAllOrdered();
}
