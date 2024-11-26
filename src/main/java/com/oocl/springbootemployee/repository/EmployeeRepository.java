package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public List<Employee> getAllByGender(Gender gender);
    public Page<Employee> findAll(Pageable pageable);
    public Employee deleteById(int id);
}
