package com.planitsquare.holidayCountry.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info()
                .title("Holiday-Country API Docs")
                .description("Planitsquare Api")
                .version("v1.0.0"));
    }

}
