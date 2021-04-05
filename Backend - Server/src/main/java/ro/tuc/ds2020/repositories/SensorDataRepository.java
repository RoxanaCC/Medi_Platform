package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.SensorData;

import java.util.UUID;

public interface SensorDataRepository extends JpaRepository<SensorData, UUID> {

}
