package com.beads.vaadin.configuration;

import com.vaadin.spring.annotation.EnableVaadin;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
}
