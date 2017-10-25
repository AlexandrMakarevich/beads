package com.beads.model.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

/**
 * Created by alexey.dranchuk on 28.09.14.
 *
 */

@Configuration
@ComponentScan(basePackages = {"com.beads.model"})
@PropertySource("classpath:/${env:dev}_db.properties")
@EnableTransactionManagement(proxyTargetClass = true)
public class HibernateConfigurationTest {

    @Autowired
    private Environment env;

    @Bean(name = "dataSource")
    public DataSource beadDSTest() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getProperty("driver", "com.mysql.jdbc.Driver"));
        ds.setUsername(env.getProperty("username", "beads"));
        ds.setPassword(env.getProperty("password", "beads"));
        ds.setUrl(env.getProperty("url", "jdbc:mysql://localhost:3306/beads3"));
        return ds;
    }

}
