package by.novik.caloriecounter.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
@SecurityScheme(name = "JWT", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenAPIDefinition() {
        return new OpenAPI().info(new Info()
                .summary("my calorie-counter REST project")
                .description("This is an online calorie counting app. With it, you can count the calories consumed and burned per day.")
                .title("REST CalorieCounter app").version("1.0.0")
                .contact(new Contact()
                        .name("Polina")
                ));
    }


}
