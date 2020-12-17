package com.vish.jpa_demo.dao;

import com.vish.jpa_demo.model.Student;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Slf4j
@Data
@Repository
public class StudentDaoImpl implements IStudentDao
{
    private Session session = null;

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    private EntityManager entityManager ;

    private  void  setEntityMangerFactoryCurrentSession()
    {
        if ( this.session == null  )
        {
            //this.session =  this.entityManagerFactory.createEntityManager().unwrap(Session.class).getSessionFactory().openSession();
            this.session =  this.entityManagerFactory.createEntityManager().unwrap(Session.class).getSessionFactory().getCurrentSession();
        }
    }

    @Override
    public void addStudent(Student student)
    {
        log.info("Adding Student " + student.getSname());
        setEntityMangerFactoryCurrentSession();
        this.session.save(student);
    }

    @Override
    public Student getStudentDetailsByName(String name)
    {
        Student invalidstudent = null ;
       updateStudentMarks(name);
        return invalidstudent;
    }

    @Transactional(propagation = Propagation.NESTED)
    private void updateStudentMarks(String name)
    {
        try
        {
            setEntityMangerFactoryCurrentSession();
            String myQry = "update Student set marks=100 where sname=:lname";
            Query query = session.createQuery(myQry);
            query.setParameter("lname",name);
            int count = query.executeUpdate();
            this.session.flush();

            log.info("No of rows updated = " + count);
            System.out.println(" No od rows updated : " + count);

         }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
