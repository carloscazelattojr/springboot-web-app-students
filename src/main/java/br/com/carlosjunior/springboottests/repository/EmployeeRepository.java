package br.com.carlosjunior.springboottests.repository;

import br.com.carlosjunior.springboottests.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);


    @Query("SELECT e FROM Employee e " +
            " WHERE e.firstName = :firstName" +
            " AND e.lastName = :lastName ")
    Employee findByJPQL(String firstName, String lastName);
}
