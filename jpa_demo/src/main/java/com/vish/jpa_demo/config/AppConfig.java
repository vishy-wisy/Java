package com.vish.jpa_demo.config;


import com.vish.jpa_demo.service.DBService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
@Configuration
public class AppConfig
{
    @Autowired
    private Environment env;

    @Bean
    public JpaVendorAdapter getJpaVendorAdapter()
    {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(false);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
        return jpaVendorAdapter;
    }


    @Bean
    public DataSource getHikariDataSource()
    {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("XXXX");
        dataSource.setPassword("XXXX");
        dataSource.setAutoCommit(false);
        //dataSource.setMinEvictableIdleTimeMillis(1000);
        //dataSource.setCacheState(false);s
        dataSource.setConnectionTimeout(31000);
        dataSource.setIdleTimeout(30000);
        dataSource.setMaximumPoolSize(2);
        dataSource.setMinimumIdle(0);
        dataSource.setConnectionTimeout(60000);


        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityMangerFactory()
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(getHikariDataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(getJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(DBProperties());
        entityManagerFactoryBean.setPersistenceUnitName("com.vish.jpa_demo");
        entityManagerFactoryBean.setPackagesToScan("com.vish.jpa_demo.model");
        return entityManagerFactoryBean;
    }


    @Bean
    public PlatformTransactionManager hibernateTransactionManager()
    {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getEntityMangerFactory().getNativeEntityManagerFactory());
        transactionManager.setDataSource(getHikariDataSource());
        return transactionManager;
    }
    private final Properties DBProperties()
    {
        final Properties hibernateProperties = new Properties();

        hibernateProperties.put("javax.persistence.transactionType", "RESOURCE_LOCAL");

        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        hibernateProperties.setProperty("hibernate.show_sql", "true");

       //hibernateProperties.setProperty("hibernate.current_session_context_class","org.springframework.orm.hibernate5.SpringSessionContext");
       hibernateProperties.setProperty("hibernate.current_session_context_class","org.hibernate.context.internal.ThreadLocalSessionContext");

        return hibernateProperties;
    }

}
