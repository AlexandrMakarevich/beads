package com.beads.vaadin.configuration;

import com.vaadin.spring.annotation.EnableVaadin;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.internal.ScopedEventBus;
import org.vaadin.spring.events.support.ApplicationContextEventBroker;

@Configuration
@EnableVaadin
@ComponentScan(basePackages = { "com.beads.vaadin", "com.beads.model", "com.beads.db"})
public class AppContextConfig {

  @Resource(mappedName = "jdbc/MySQLDS")
  private DataSource dataSource;

  @Bean
  public DataSource dataSource() {
    return  dataSource;
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public EventBus defaultEventBus() {
    return new ScopedEventBus.DefaultSessionEventBus(null);
  }

  @Bean
  public ApplicationContextEventBroker applicationContextEventBroker(EventBus applicationEventBus) {
    return new ApplicationContextEventBroker(applicationEventBus);
  }
}
