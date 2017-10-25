package com.beads.model.config;

import com.tacitknowledge.util.migration.jdbc.AutoPatchService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

/**
 * Created by alexey.dranchuk on 13.09.14.
 *
 */

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

    public static final String TRANSACTION_MANAGER_NAME = "hibernateTransactionManager";

    public static final String AUTO_PATCHER_BEAN_NAME = "autoPatcher";

    @Autowired
    private DataSource dataSource;

    @Bean(name = AUTO_PATCHER_BEAN_NAME, initMethod = "patch" )
    public AutoPatchService buildAutoPatcher() {
        AutoPatchService autoPatchService = new AutoPatchService();
        autoPatchService.setSystemName("aaa");
        autoPatchService.setPatchPath("db_scripts");
        autoPatchService.setDatabaseType("mysql");
        autoPatchService.setDataSource(dataSource);
        return autoPatchService;
    }

    @Bean
    @DependsOn(AUTO_PATCHER_BEAN_NAME)
    public LocalSessionFactoryBean hibernateSessionFactory() throws Exception {
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(dataSource);
        lsfb.setPackagesToScan("com.beads.model.domain");
        return lsfb;
    }

    @Bean(name=TRANSACTION_MANAGER_NAME)
    @Primary
    public HibernateTransactionManager hibernateTransactionManager(SessionFactory sf) throws Exception {
        return  new HibernateTransactionManager(sf);
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
}
