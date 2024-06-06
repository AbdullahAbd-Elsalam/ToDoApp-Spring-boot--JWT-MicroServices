package com.toda.config;

 import io.swagger.v3.oas.annotations.OpenAPIDefinition;
 import io.swagger.v3.oas.annotations.info.Contact;
 import io.swagger.v3.oas.annotations.info.Info;
 import io.swagger.v3.oas.models.ExternalDocumentation;
 import io.swagger.v3.oas.models.OpenAPI;
 import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "Item API",
                version = "1.0",
                description = "API for managing items",
                contact = @Contact(name = "abdullah", email = "your-email@example.com")
        )
)
public class ApplicationConfig {



    // to create swagger api
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .externalDocs(new ExternalDocumentation()
                        .description("Item API Documentation")
                        .url("http://localhost:8083/swagger-ui.html"));
    }
}
