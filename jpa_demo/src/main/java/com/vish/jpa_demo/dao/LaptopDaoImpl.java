package com.vish.jpa_demo.dao;

import com.vish.jpa_demo.model.Laptop;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Data
public class LaptopDaoImpl implements ILaptopDao
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
    public void addLaptop(Laptop laptop)
    {
        log.info("Adding Laptop " + laptop.getBrand());
        setEntityMangerFactoryCurrentSession();
        this.session.save(laptop);

    }

    @Override
    public Laptop getLaptopDetailsByName(String name)
    {
        Laptop invalidlaptop = null ;

        try
        {

            setEntityMangerFactoryCurrentSession();
            String myQry = "from Laptop where brand = :bname";
            Query query = session.createQuery(myQry,Laptop.class);
            query.setParameter("bname",name);

            query.setFetchSize(1);
            ScrollableResults scrollableResults = query.setReadOnly(true).scroll(ScrollMode.FORWARD_ONLY);
            while(true)
            {
                for (;scrollableResults.next();  )
                {
                    List<Object> objectList = Arrays.asList(scrollableResults.get());
                    Optional<Laptop> laptop  = objectList
                            .stream()
                            .map(e -> (Laptop) e )
                            .filter(e2  -> e2.getBrand().equalsIgnoreCase(name))
                            .findFirst();

                    return laptop.orElse(invalidlaptop);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return invalidlaptop;
    }
}
