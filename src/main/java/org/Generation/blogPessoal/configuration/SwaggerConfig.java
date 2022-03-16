package org.Generation.blogPessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

    @Bean
	public OpenAPI springBlogPessoalOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("Projeto - Blog Pessoal")
					.description("Blog pessoal desenvolvido no Bootcamp Dev Java Jr Full Stack -> Generation Brasil")
					.version("v0.0.1")
				.license(new License()
					.name("Generation Brasil")
					.url("https://brazil.generation.org/"))
				.contact(new Contact()
					.name("Caique Rodrigues")
					.url("https://github.com/daawch")
					.email("caique.rdg.0@gmail.com")))
				.externalDocs(new ExternalDocumentation()
					.description("Github")
					.url("https://github.com/daawch/Blog_Pessoal"));
	}

    @Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Sucesso."));
				apiResponses.addApiResponse("201", createApiResponse("Criado."));
				apiResponses.addApiResponse("400", createApiResponse("Erro na requisição."));
				apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado."));
				apiResponses.addApiResponse("404", createApiResponse("Objeto não encontrado."));
				apiResponses.addApiResponse("500", createApiResponse("Erro interno do servidor."));

			}));
		};
	}

	private ApiResponse createApiResponse(String message) {
		return new ApiResponse().description(message);
	}

}