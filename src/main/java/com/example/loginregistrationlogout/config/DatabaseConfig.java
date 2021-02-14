package com.example.loginregistrationlogout.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:db.properties")
public class DatabaseConfig {

    @Autowired
    private Environment env;

    @Bean
    public ComboPooledDataSource comboPooledDataSource() throws PropertyVetoException {

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        comboPooledDataSource.setUser(env.getProperty("jdbc.username"));
        comboPooledDataSource.setPassword(env.getProperty("jdbc.password"));
        comboPooledDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        comboPooledDataSource.setDriverClass(env.getProperty("jdbc.driver"));

        comboPooledDataSource.setInitialPoolSize(getIntegerValue(env.getProperty("connection.initialPoolSize")));
        comboPooledDataSource.setMaxPoolSize(getIntegerValue(env.getProperty("connection.maxPoolSize")));
        comboPooledDataSource.setMinPoolSize(getIntegerValue(env.getProperty("connection.minPoolSize")));
        comboPooledDataSource.setMaxIdleTime(getIntegerValue(env.getProperty("connection.maxIdleTime")));

        return comboPooledDataSource;
    }

    @Bean
    @Autowired
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {

        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        factoryBean.setDataSource(dataSource);

        Properties pros = new Properties();
        pros.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        pros.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        pros.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        factoryBean.setHibernateProperties(pros);

        return factoryBean;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {

        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);

        return transactionManager;
    }


    public int getIntegerValue(String value){
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException exp){
            exp.printStackTrace();
            return 0;
        }
    }

}
