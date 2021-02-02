package com.vish.hiber.LockingDemo.service;

import com.vish.hiber.LockingDemo.dao.EmployeeDAO;
import com.vish.hiber.LockingDemo.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Data
@AllArgsConstructor
@Component
@Slf4j
public class EmployeeService
{
    @Autowired
    private EmployeeDAO employeeDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    public void addEmployee(Employee employee) throws Exception
    {
            employeeDAO.addEmployee(employee);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    public Employee getEmployeeDetailsByName(String name)
    {
        return employeeDAO.getEmployeebyNameWithScrollable(name);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    public List<Employee> getEmployeeList()
    {
        return employeeDAO.getEmploeeList();
    }
}
