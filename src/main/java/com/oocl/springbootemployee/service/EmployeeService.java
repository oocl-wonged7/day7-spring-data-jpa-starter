package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.exception.EmployeeAgeNotValidException;
import com.oocl.springbootemployee.exception.EmployeeAgeSalaryNotMatchedException;
import com.oocl.springbootemployee.exception.EmployeeInactiveException;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeInMemoryRepository;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeInMemoryRepository employeeInMemoryRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeInMemoryRepository employeeInMemoryRepository, EmployeeRepository employeeRepository) {
        this.employeeInMemoryRepository = employeeInMemoryRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> findAll(Gender gender) {
        return employeeRepository.getAllByGender(gender);
    }

    public List<Employee> findAll(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page-1, pageSize)).getContent();
    }

    public Employee findById(Integer employeeId) {
        return employeeInMemoryRepository.findById(employeeId);
    }

    public Employee create(Employee employee) {
        if (employee.getAge() < 18 || employee.getAge() > 65)
            throw new EmployeeAgeNotValidException();
        if (employee.getAge() >= 30 && employee.getSalary() < 20000.0)
            throw new EmployeeAgeSalaryNotMatchedException();

        employee.setActive(true);
        return employeeRepository.save(employee);
    }

    public Employee update(Integer employeeId, Employee employee) {
        Employee employeeExisted = employeeRepository.getById(employeeId);
        employeeExisted.setName(employee.getName() == null ? employeeExisted.getName() : employee.getName());
        employeeExisted.setGender(employee.getGender() == null ? employeeExisted.getGender() : employee.getGender());
        employeeExisted.setAge(employee.getAge() == null ? employeeExisted.getAge() : employee.getAge());
        employeeExisted.setSalary(employee.getSalary() == null ? employeeExisted.getSalary() : employee.getSalary());
        employeeExisted.setActive(employee.getActive() == null ? employeeExisted.getActive() : employee.getActive());
        if (!employeeExisted.getActive())
            throw new EmployeeInactiveException();

        return employeeRepository.save(employeeExisted);
    }

    public void delete(Integer employeeId) {
        employeeInMemoryRepository.deleteById(employeeId);
    }
}
