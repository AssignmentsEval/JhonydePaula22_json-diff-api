package com.waes.test.jsondiffapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.waes.test.jsondiffapi.constants.SwaggerConstants.CONTACT_EMAIL;
import static com.waes.test.jsondiffapi.constants.SwaggerConstants.CONTACT_NAME;
import static com.waes.test.jsondiffapi.constants.SwaggerConstants.CONTACT_URL;
import static com.waes.test.jsondiffapi.constants.SwaggerConstants.DESCRIPTION;
import static com.waes.test.jsondiffapi.constants.SwaggerConstants.TITLE;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.waes.test.jsondiffapi.controller"))
                .build()
                .apiInfo(metaData())
                .useDefaultResponseMessages(false);

    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .contact(new Contact(CONTACT_NAME,
                        CONTACT_URL,
                        CONTACT_EMAIL))
                .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
