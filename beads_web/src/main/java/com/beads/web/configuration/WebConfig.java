package com.beads.web.configuration;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by alexey.dranchuk on 12.09.14.
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.beads.web.webcontroller"})
@Import({ThymeleafConfig.class})
public class WebConfig implements WebMvcConfigurer {

    private static final boolean jackson2Present =
    ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", WebConfig.class.getClassLoader()) &&
            ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", WebConfig.class.getClassLoader());

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (jackson2Present)
            converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
