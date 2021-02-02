package com.vish.hiber.LockingDemo;

import com.vish.hiber.LockingDemo.model.Employee;
import com.vish.hiber.LockingDemo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@SpringBootApplication
public class LockingDemoApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(LockingDemoApplication.class, args);
		log.info("I am entering into main");
		ApplicationContext appContext = new AnnotationConfigApplicationContext(LockingDemoApplication.class);


		Employee emp1 = new Employee("N553434", "Vish", "Java");
		Employee emp2 = new Employee("N78282", "KD", "Java");
		Employee emp3 = new Employee("F38282", "Gaurav", "C++");

		EmployeeService employeeService = appContext.getBean(EmployeeService.class);
		try
		{
			employeeService.addEmployee(emp1);
		}
		catch (SQLException sqle)
		{
			log.info("Error Code :  " + sqle.getErrorCode() );
			log.info(" " + sqle.getSQLState());
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}

		List<Employee> employeeList = employeeService.getEmployeeList();
		//System.out.println(employeeService.getEmployeeDetailsByName("Vish"));
		log.info("I am exiting from main");

	}

}
