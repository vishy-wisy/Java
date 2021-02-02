package com.vish.hiber.LockingDemo.dao;


import com.vish.hiber.LockingDemo.model.Employee;
import lombok.Data;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@Data
public class EmployeeDAOImpl implements EmployeeDAO {
    private Session session = null;

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void addEmployee(Employee emp) throws Exception {
        System.out.println("Adding new Employee " + emp.getEname());
        this.session = (this.session != null) ?
                this.session : this.sessionFactory.getCurrentSession();
        this.session.save(emp);
    }

    @Override
    public void updateEmployee(Employee e) {

        System.out.println("Updating  Employee " + e.getEname());
    }

    @Override
    public void deleteEmployee(Employee e) {
        System.out.println("Deleting Employee " + e.getEname());
    }


    @Override
    public Employee getEmployeebyNameWithScrollable(String name) {
        Employee invalidEmployee = null;
        try {
            this.session = (this.session != null && this.session.isOpen() && this.session.isConnected()) ?
                    this.session : this.sessionFactory.openSession();
            String myQry = "from Employee ";
            Query query = session.createQuery(myQry, Employee.class);

            query.setFetchSize(1);
            ScrollableResults scrollableResults = query.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
            while (true) {
                for (; scrollableResults.next(); ) {
                    Employee employee = (Employee) scrollableResults.get()[0];
                    List<Employee> employeeList = new ArrayList<>();
                    employeeList.add(employee);
                    Optional<Employee> employee2 = employeeList.stream().filter(e -> e.getEname().equalsIgnoreCase(name)).findFirst();
                    return employee2.orElse(invalidEmployee);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invalidEmployee;
    }

    @Override
    public List<Employee> getEmploeeList() {
        List<Employee> employeeList = null;
        this.session = this.sessionFactory.getCurrentSession();
        String myQry = "from Employee";

        employeeList = session.createQuery(myQry, Employee.class)
                .setMaxResults(1)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getResultList();

        Employee e = employeeList.get(0);


        return employeeList;
    }

}
