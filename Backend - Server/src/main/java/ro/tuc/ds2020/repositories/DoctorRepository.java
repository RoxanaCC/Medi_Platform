package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.Doctor;

import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID>{

    @Query(value = "SELECT d FROM Doctor d " +
            "INNER JOIN Person p ON d.person = p.id " +
            "ORDER BY p.name")
    List<Doctor> getAllOrdered();

    @Query(value = "SELECT d FROM Doctor d " +
            "INNER JOIN Person p ON d.person = p.id " +
            "WHERE p.username = :username " +
            "and p.password = :password")
    public Doctor find(@Param("username") String username, @Param("password") String password);
}
