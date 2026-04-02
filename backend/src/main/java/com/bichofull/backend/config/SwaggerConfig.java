package com.bichofull.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bichofullOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bichofull API")
                        .description("Documentação completa dos endpoints do Jogo do Bicho Full")
                        .version("v1.0.0"));
    }
}