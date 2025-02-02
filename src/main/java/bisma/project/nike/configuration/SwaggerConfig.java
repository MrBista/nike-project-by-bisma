package bisma.project.nike.configuration;
import java.util.List;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
//@OpenAPIDefinition(
//        info = @Info(
//                title = "Claims Service",
//                version = "1.0",
//                description = "Claims Information"
//        ),
//        security = {
//                @SecurityRequirement(name = "bearerAuth")
//        }
//)
//@SecurityScheme(
//        name = "bearerAuth",
//        description = "JWT authentication",
//        scheme = "bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
public class SwaggerConfig {
    @Value("${olshop.openapi.dev-url}")
    private String devUrl;

    @Value("${olshop.openapi.prod-url}")
    private String prodUrl;

    String schemeName = "bearerAuth";
    String bearerFormat = "JWT";
    String scheme = "bearer";
    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("gustibisman2308@gmail.com");
        contact.setName("Gusti Bisman Taka");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("E-Commerce API")
                .version("1.0")
                .description("This API exposes endpoints to manage api online e commerce.")
                .license(mitLicense);

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                .addList(schemeName)).components(new Components()
                        .addSecuritySchemes(
                                schemeName, new SecurityScheme()
                                        .name(schemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .bearerFormat(bearerFormat)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme(scheme)
                        )
                )
                .info(info)
                .servers(List.of(devServer, prodServer));
    }
}
