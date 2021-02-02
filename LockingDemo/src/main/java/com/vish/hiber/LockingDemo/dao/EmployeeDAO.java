package com.vish.hiber.LockingDemo.dao;


import com.vish.hiber.LockingDemo.model.Employee;

import java.util.List;

public interface EmployeeDAO
{
    void addEmployee(Employee e) throws Exception;
    void updateEmployee(Employee e);
    void deleteEmployee(Employee e);
    Employee getEmployeebyNameWithScrollable(String name);
    List<Employee> getEmploeeList();
}
