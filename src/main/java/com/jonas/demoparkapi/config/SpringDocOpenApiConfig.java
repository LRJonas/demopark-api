package com.jonas.demoparkapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean // anotação que indica que esse método é um bean, bean é um objeto que é instanciado, gerenciado e injetado pelo spring
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("security", securityScheme())) // retorna um objeto OpenAPI com os componentes de segurança
                        .info(
                                new Info()
                                    .title("Demo Park API")
                                    .description("API para gerenciamento de parques")
                                    .version("v1")
                                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                                        .contact(new Contact().name("Jonas"))
                        ); // retorna um objeto OpenAPI com as informações do título, descrição, versão e as licensas usadas
    }

    private SecurityScheme securityScheme(){
        return new SecurityScheme()
                .description("Insira um Bearer token válido para prosseguir")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT") // retorna um objeto SecurityScheme com a descrição, tipo, local, esquema e formato do token
                .name("security");
    }
}
