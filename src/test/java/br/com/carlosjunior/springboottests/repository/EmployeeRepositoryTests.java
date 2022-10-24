package br.com.carlosjunior.springboottests.repository;

import br.com.carlosjunior.springboottests.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    private Employee employee2;

    @BeforeAll
    static void setUpBeforeAll() throws Exception {
        System.out.println("setUpBeforeAll");

    }

    @BeforeEach
    void setup() throws Exception {
        System.out.println("BeforeEach");

        employee = Employee.builder()
                .firstName("Carlos")
                .lastName("Junior")
                .email("carlosjunior@carlosjunior.com.br")
                .build();

        employee2 = Employee.builder()
                .firstName("Mateus")
                .lastName("Hiroki")
                .email("mateus@cazelatto.com.br")
                .build();

    }


    @DisplayName("EmployeeRepository - Save new Employee")
    @Test
    void givenEmployee_whenSave_thenReturnSavedEmployee() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Carlos")
//                .lastName("Junior")
//                .email("carlosjunior@carlosjunior.com.br")
//                .build();
        //when - Action
        Employee saveEmployee = employeeRepository.save(employee);

        // then - verify th output
        Assertions.assertThat(saveEmployee).isNotNull();
        Assertions.assertThat(saveEmployee.getId()).isPositive();
    }

    @DisplayName("EmployeeRepository - List All Employees")
    @Test
    void givenEmployeesList_whenFindAll_thenReturnEmployeesList() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Carlos")
//                .lastName("Junior")
//                .email("carlosjunior@carlosjunior.com.br")
//                .build();
//        Employee employee2 = Employee.builder()
//                .firstName("Mateus")
//                .lastName("Hiroki")
//                .email("mateus@cazelatto.com.br")
//                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        // when - Action
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify th output
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("EmployeeRepository - Find by Id Employee")
    @Test
    void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Carlos")
//                .lastName("Junior")
//                .email("carlosjunior@carlosjunior.com.br")
//                .build();
        employeeRepository.save(employee);

        // when - Action
        Employee employeeById = employeeRepository.findById(employee.getId()).get();

        // then - verify th output
        Assertions.assertThat(employeeById).isNotNull();
    }

    @DisplayName("EmployeeRepository - Find by Email Employee")
    @Test
    void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Carlos")
//                .lastName("Junior")
//                .email("carlosjunior@carlosjunior.com.br")
//                .build();

        employeeRepository.save(employee);

        // when - Action
        Optional<Employee> employeeDB = employeeRepository.findByEmail(employee.getEmail());

        // then - verify th output
        Assertions.assertThat(employeeDB).isPresent();
        Assertions.assertThat(employeeDB.get().getEmail()).isEqualTo(employee.getEmail());

    }


    @DisplayName("EmployeeRepository - Update Employee")
    @Test
    void givenEmployeeObject_whenUpdateEmployee_thenUpdatedEmployeedObject() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Carlos")
//                .lastName("Junior")
//                .email("carlosjunior@carlosjunior.com.br")
//                .build();

        employeeRepository.save(employee);

        // when - Action
        Employee employeeSearch = employeeRepository.findById(employee.getId()).get();
        employeeSearch.setEmail("carlosjunior1983@gmail.com");
        employeeSearch.setFirstName("Eliana");
        Employee employeeSaved = employeeRepository.save(employeeSearch);

        // then - verify th output
        Assertions.assertThat(employeeSaved.getEmail()).isEqualTo(employee.getEmail());
        Assertions.assertThat(employeeSaved.getFirstName()).isEqualTo(employee.getFirstName());
    }

    @DisplayName("EmployeeRepository - Delete Employee")
    @Test
    void givenEmployee_whenDelete_thenRemoveEmployee() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Carlos")
//                .lastName("Junior")
//                .email("carlosjunior@carlosjunior.com.br")
//                .build();
        employeeRepository.save(employee);
        // when - Action
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeDeleted = employeeRepository.findById(employee.getId());

        // then - verify th output
        Assertions.assertThat(employeeDeleted).isEmpty();
    }

    @DisplayName("EmployeeRepository - Query findByJPQL")
    @Test
    void givenEmployee_whenFindbByJPQL_thenEmployeeObject() {
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Carlos")
//                .lastName("Junior")
//                .email("carlosjunior@carlosjunior.com.br")
//                .build();
        employeeRepository.save(employee);

        // when - Action
        Employee employeeJPQL = employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());

        // then - verify th output
        Assertions.assertThat(employeeJPQL).isNotNull();
        Assertions.assertThat(employeeJPQL.getFirstName()).isEqualTo(employee.getFirstName());
        Assertions.assertThat(employeeJPQL.getLastName()).isEqualTo(employee.getLastName());
    }

  }
