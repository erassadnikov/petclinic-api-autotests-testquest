package dev.ers.petclinic;

import dev.ers.petclinic.client.OwnerClient;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class ApiTestConfig {
    @Value("${petclinic.base-url}")
    private String baseUrl;

    /** Настраивает базовый URL, таймауты, HTTP-логи и вложения Allure. */
    @Bean
    RequestSpecification requestSpecification() {
        HttpClientConfig httpClientConfig = HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 5_000)
                .setParam("http.socket.timeout", 10_000);
        RestAssuredConfig config = RestAssuredConfig.config()
                .httpClient(httpClientConfig);

        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setConfig(config)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new AllureRestAssured())
                .build();
    }

    /** Регистрирует API-клиент, который Spring передает в тесты. */
    @Bean
    OwnerClient ownerClient(RequestSpecification specification) {
        return new OwnerClient(specification);
    }

}
