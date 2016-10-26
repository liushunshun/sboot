package com.xy.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ErrorPageConfig extends WebMvcConfigurerAdapter {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
            ErrorPage error505Page = new ErrorPage(HttpStatus.HTTP_VERSION_NOT_SUPPORTED, "/505");
            ErrorPage error506Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/405");
            container.addErrorPages(error401Page, error404Page, error500Page, error505Page, error506Page);
        };
    }
}
