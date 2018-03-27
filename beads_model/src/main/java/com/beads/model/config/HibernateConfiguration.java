package com.beads.model.config;

import static com.beads.db.config.FlywayConfiguration.FLYWAY_BEAN;
import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by alexey.dranchuk on 13.09.14.
 *
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

    public static final String PROPERTIES_PATH = "/hibernate.properties";
    public static final String PACKAGE_TO_SCAN = "com.beads.model.domain";

    @Autowired
    private DataSource dataSource;

    @DependsOn(FLYWAY_BEAN)
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws IOException {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManager.setPackagesToScan(PACKAGE_TO_SCAN);
        Resource resource = new ClassPathResource(PROPERTIES_PATH);
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        entityManager.setJpaProperties(props);
        return entityManager;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws IOException {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }
}
