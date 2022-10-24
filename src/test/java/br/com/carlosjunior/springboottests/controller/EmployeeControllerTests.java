package br.com.carlosjunior.springboottests.controller;

import br.com.carlosjunior.springboottests.entity.Employee;
import br.com.carlosjunior.springboottests.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;


    private Long idEmployee = 1L;
    private Employee employee;
    private Employee employee2;


    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("Carlos")
                .lastName("Junior")
                .email("carlosjunior@carlosjunior.com.br")
                .build();

        employee2 = Employee.builder()
                .firstName("Tony")
                .lastName("Stark")
                .email("tony@gmail.com")
                .build();
    }


    @DisplayName("EmployeeController - createEmployee()")
    @Test
    void givenEmployee_whenCreateEmployee_thenEmployeeCreated() throws Exception {

        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when - Action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify th output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    @DisplayName("EmployeeController - getAllEmployee()")
    @Test
    void givenListOfEmployees_whenGetAllEmployees_thenEmployeesList() throws Exception {

        // given - precondition or setup
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        employeeList.add(employee2);
        BDDMockito.given(employeeService.getAllEmployees()).willReturn(employeeList);

        // when - Action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));

        // then - verify th output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employeeList.size())));
    }

    @DisplayName("EmployeeController - getEmployeeById()")
    @Test
    void givenEmployee_whenGetEmployeeById_thenEmployeeObjectById() throws Exception {
        // given - precondition or setup
        Long id = 1L;
        BDDMockito.given(employeeService.getEmployeeById(id)).willReturn(Optional.of(employee));

        // when - Action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", id));

        // then - verify th output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }


    @DisplayName("EmployeeController - getEmployeeById() - Empty")
    @Test
    void givenEmployee_whenGetEmployeeById_thenEmployeeObjectEmpty() throws Exception {
        // given - precondition or setup
        Long id = 1L;
        BDDMockito.given(employeeService.getEmployeeById(id)).willReturn(Optional.empty());

        // when - Action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", id));

        // then - verify th output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("EmployeeController - updateEmployee()")
    @Test
    void givenUpdateEmployee_whenUpdateEmployee_thenUpdateEmployee() throws Exception {
        // given - precondition or setup
        BDDMockito.given(employeeService.getEmployeeById(idEmployee)).willReturn(Optional.of(employee));
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));


        // when - Action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", idEmployee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee2)));

        // then - verify th output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee2.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee2.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee2.getEmail())))
                ;
    }

    @DisplayName("EmployeeController - updateEmployee() - id NotFound")
    @Test
    void givenUpdateEmployee_whenUpdateEmployee_then404() throws Exception {
        // given - precondition or setup
        BDDMockito.given(employeeService.getEmployeeById(idEmployee)).willReturn(Optional.empty());
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));


        // when - Action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", idEmployee)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee2)));

        // then - verify th output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())

        ;
    }


}
