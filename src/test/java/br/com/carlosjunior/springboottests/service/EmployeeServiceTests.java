package br.com.carlosjunior.springboottests.service;

import br.com.carlosjunior.springboottests.entity.Employee;
import br.com.carlosjunior.springboottests.repository.EmployeeRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private Employee employee2;

    @BeforeEach
    public void setup() {
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeService(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Carlos")
                .lastName("Junior")
                .email("carlosjunior@carlosjunior.com.br")
                .build();

        employee2 = Employee.builder()
                .id(1L)
                .firstName("Tony")
                .lastName("Stark")
                .email("tony@gmail.com")
                .build();
    }

    @DisplayName("EmployeeService - method saveEmployee()")
    @Test
    void givenEmployeeObject_whenSaveEmployee_thenSavedEmployee() {
        // given - precondition or setup
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        // when - Action
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify th output
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getEmail()).isEqualTo(employee.getEmail());

    }

    @DisplayName("EmployeeService - method saveEmployee() with thows ResourceAccessException")
    @Test
    void givenExistEmail_whenSaveEmployee_thenThrowsException() {
        // given - precondition or setup
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        org.junit.jupiter.api.Assertions.assertThrows(ResourceAccessException.class, () -> {
            Employee savedEmployee = employeeService.saveEmployee(employee);
        });

        // then - verify th output
        Mockito.verify(employeeRepository, Mockito.never()).save(Mockito.any(Employee.class));

    }

    @DisplayName("EmployeeService - method getAllEmployees() - With Employees")
    @Test
    void givenEmployeesList_whenAllEmployees_thenReturnEmployeesList(){
        // given - precondition or setup
        BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee,employee2));

        // when - Action
        List<Employee> list = employeeService.getAllEmployees();

        // then - verify th output
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list.size()).isEqualTo(2);
    }
    @DisplayName("EmployeeService - method getAllEmployees() - Empty List")
    @Test
    void givenEmptyEmployeesList_whenEmptyAllEmployees_thenReturnEmptyEmployeesList(){
        // given - precondition or setup
        BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when - Action
        List<Employee> list = employeeService.getAllEmployees();

        // then - verify th output
        Assertions.assertThat(list).isEmpty();
        Assertions.assertThat(list.size()).isEqualTo(0);
    }

    @DisplayName("EmployeeService - method getEmployeeById()")
    @Test
    void givenEmployeeId_whenGetEmployeeById_thenEmployeeObject(){
        // given - precondition or setup
        BDDMockito.given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when - Action
        Employee employeeSaved = employeeService.getEmployeeById(employee.getId()).get();

        // then - verify th output
        Assertions.assertThat(employeeSaved).isNotNull();
        Assertions.assertThat(employeeSaved.getId()).isEqualTo(employee.getId());
    }


    @DisplayName("EmployeeService - method updateEmployee()")
    @Test
    void givenEmployee_whenUpdateEmployee_thenEmployeeObject(){
        // given - precondition or setup
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("carlos@unicesumar.edu.br");
        employee.setFirstName("Carlitos");

        // when - Action
        Employee savedEmployee =  employeeService.updateEmployee(employee);

        // then - verify th output
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getEmail()).isEqualTo("carlos@unicesumar.edu.br");
        Assertions.assertThat(savedEmployee.getFirstName()).isEqualTo("Carlitos");

    }

    @DisplayName("EmployeeService - method deleteEmployee()")
    @Test
    void givenEmployee_whenDeleteEmployee_thenDeleted(){
        Long id =1L;

        // given - precondition or setup
        BDDMockito.willDoNothing().given(employeeRepository).deleteById(id);

        // when - Action
        employeeService.deleteEmployee(id);

        // then - verify th output
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(id);
    }




}
