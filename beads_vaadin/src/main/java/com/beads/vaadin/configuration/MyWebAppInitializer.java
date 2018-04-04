package com.beads.vaadin.configuration;

import com.vaadin.spring.server.SpringVaadinServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebAppInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.register(AppContextConfig.class, WebConfig.class);
    registerSpringContextLoaderListener(servletContext, context);
    registerVaadinServlet(servletContext);
    registerSpringServlet(servletContext, context);
  }

  private void registerVaadinServlet(ServletContext servletContext) {
    ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
        "vaadin", SpringVaadinServlet.class);
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/*");
  }

  private void registerSpringServlet(ServletContext servletContext,
                                     AnnotationConfigWebApplicationContext applicationContext) {
    ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
        "dispatcher", new DispatcherServlet(applicationContext));
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/admin");
  }

  private void registerSpringContextLoaderListener(
      ServletContext servletContext,
      AnnotationConfigWebApplicationContext rootContext) {
    servletContext.addListener(new ContextLoaderListener(rootContext));
//    servletContext.addListener(new ContextCleanupListener());
//    servletContext.addListener(new RequestContextListener());
  }
}
