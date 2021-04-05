package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.MedPlan;

import java.util.List;
import java.util.UUID;

public interface MedPlanRepository extends JpaRepository<MedPlan, UUID>{

    @Query(value = "SELECT mp " +
            "FROM MedPlan mp " +
            "ORDER BY mp.id")
    List<MedPlan> getAllOrdered();
}
