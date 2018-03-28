package com.beads.vaadin.configuration;

import com.vaadin.spring.server.SpringVaadinServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class MyWebAppInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.register(AppContextConfig.class);
    registerSpringContextLoaderListener(servletContext, context);
    registerServlet(servletContext);
  }

  private void registerServlet(ServletContext servletContext) {
    ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
        "vaadin", SpringVaadinServlet.class);
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/*");
  }

  private void registerSpringContextLoaderListener(
      ServletContext servletContext,
      AnnotationConfigWebApplicationContext rootContext) {
    servletContext.addListener(new ContextLoaderListener(rootContext));
//    servletContext.addListener(new ContextCleanupListener());
//    servletContext.addListener(new RequestContextListener());
  }
}
