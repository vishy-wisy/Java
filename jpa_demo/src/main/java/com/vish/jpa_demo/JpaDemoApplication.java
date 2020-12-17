package com.vish.jpa_demo;

import com.vish.jpa_demo.config.AppConfig;
import com.vish.jpa_demo.model.Laptop;
import com.vish.jpa_demo.model.Student;
import com.vish.jpa_demo.service.DBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
public class JpaDemoApplication
{

	public static void main(String[] args)
	{
		log.info("Starting Main");
		SpringApplication.run(JpaDemoApplication.class, args);

		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JpaDemoApplication.class);

		Student s = new Student("Vish");
		Laptop laptop = new Laptop("HP");

		s.setLaptop(laptop);

		DBService dbService =applicationContext.getBean(DBService.class);

		try
		{
			dbService.addLaptop(laptop);
			dbService.addStudent(s);
			laptop.setBrand("Apple");
			dbService.addLaptop(laptop);
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}


		log.info("Exiting Main");

	}

}
