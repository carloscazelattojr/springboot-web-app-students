package br.com.carlosjunior.springboottests.service;

import br.com.carlosjunior.springboottests.entity.Employee;
import br.com.carlosjunior.springboottests.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        log.info("[EmployeeService.saveEmployee] - {}",employee.toString());
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent())
            throw new ResourceAccessException("Employee already salved with email");

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        log.info("[EmployeeService.getAllEmployees] - Into");
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        log.info("[EmployeeService.getEmployeeById] - {}",id);
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Employee employee){
        log.info("[EmployeeService.updateEmployee] - {}",employee);
        return employeeRepository.save(employee);
    }


    public void deleteEmployee(Long id){
        log.info("[EmployeeService.deleteEmployee] - {}",id);
        employeeRepository.deleteById(id);
    }

}
