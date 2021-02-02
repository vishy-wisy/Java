package com.vish.hiber.LockingDemo.config;


import com.vish.hiber.LockingDemo.model.Employee;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.vish.dbtest"})
public class AppConfig
{
    @Autowired
    private Environment env;



    @Bean
    public PlatformTransactionManager hibernateTransactionManager()
    {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        transactionManager.setDataSource(restDataSource());
        return transactionManager;
    }


    @Bean
    public DataSource restDataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("");
        dataSource.setPassword("");
        dataSource.setDefaultAutoCommit(false);
        //dataSource.setMinEvictableIdleTimeMillis(1000);
        dataSource.setTimeBetweenEvictionRunsMillis(1000);
        //dataSource.setCacheState(false);
       // dataSource.setInitialSize(0);
        dataSource.setMaxIdle(0);
        dataSource.setMinIdle(0);
        dataSource.setMaxActive(1);
        dataSource.setInitialSize(1);

        return dataSource;
    }
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean  sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(restDataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setAnnotatedClasses(Employee.class);
        return sessionFactory;
    }

    private final Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        hibernateProperties.setProperty("hibernate.show_sql", "true");

//        hibernateProperties.setProperty("current_session_context_class","thread");

        hibernateProperties.setProperty("hibernate.dbcp.initialSize","5");
        hibernateProperties.setProperty("hibernate.dbcp.maxTotal","20");
       hibernateProperties.setProperty("hibernate.dbcp.maxIdle","10");

        hibernateProperties.setProperty("hibernate.dbcp.minIdle","5");
        hibernateProperties.setProperty("hibernate.dbcp.maxWaitMillis","-1");
        hibernateProperties.setProperty("hibernate.connection.autocommit","false");
        hibernateProperties.setProperty("logging.level.org.hibernate.SQL","debug");

        //hibernateProperties.setProperty("connection.pool_size","1");

        return hibernateProperties;
    }

}
