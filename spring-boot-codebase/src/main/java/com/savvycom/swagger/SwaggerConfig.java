package com.savvycom.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String REST_API = "/api";

    @Bean
    public Docket api() {
        ApiInfo restAPIInfo = buildApiInfo(REST_API);

        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.savvycom.swagger"))
                .paths(regex(".*" + REST_API + ".*"))
                .build()
                .apiInfo(restAPIInfo).globalOperationParameters(aParameters);
    }

    private ApiInfo buildApiInfo(String version) {
        return new ApiInfoBuilder()
                .title("Spring API Documentation")
                .description("Fresher API")
                .version(version)
                .build();
    }
}
