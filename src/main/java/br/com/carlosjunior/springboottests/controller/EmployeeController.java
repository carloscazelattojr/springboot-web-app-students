package br.com.carlosjunior.springboottests.controller;

import br.com.carlosjunior.springboottests.entity.Employee;
import br.com.carlosjunior.springboottests.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Employee> getByIdEmployee(@PathVariable Long id) {
        return employeeService
                .getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.getEmployeeById(id).map(item -> {
            return new ResponseEntity<>(employeeService.updateEmployee(item), HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
