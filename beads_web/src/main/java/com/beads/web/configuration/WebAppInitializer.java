package com.beads.web.configuration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.*;

/**
 * Created by alexey.dranchuk on 12.09.14.
 *
 */

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        addCharacterEncodingFilter(servletContext);
    }

    private void addCharacterEncodingFilter(ServletContext servletContext) {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", filter);
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{AppContext.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
