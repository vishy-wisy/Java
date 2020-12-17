package com.vish.jpa_demo.service;

import com.vish.jpa_demo.dao.ILaptopDao;
import com.vish.jpa_demo.dao.IStudentDao;
import com.vish.jpa_demo.model.Laptop;
import com.vish.jpa_demo.model.Student;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Data
@Slf4j
@Component
public class DBService
{
    private Session session = null;

    @Autowired(required = false )
    private SessionFactory sessionFactory;

    @Autowired
    private IStudentDao studentDao;

    @Autowired
    private ILaptopDao laptopDao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void addStudent(Student student) throws Exception
    {
        try
        {
            studentDao.addStudent(student);
        }
       

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void addLaptop(Laptop laptop) throws Exception
    {
        try
        {
            laptopDao.addLaptop(laptop);
        }


        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
