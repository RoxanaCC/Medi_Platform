package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.Person;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    List<Person> findByName(String name);

    @Query(value = "SELECT p " +
            "FROM Person p " +
            "ORDER BY p.name")
    List<Person> getAllOrdered();

    @Query(value = "SELECT p FROM Person p " +
            "WHERE p.username = :username " +
            "and p.password = :password")
    public Person findByLogin(@Param("username") String username,@Param("password") String password);

}
