package dev.ers.petclinic.tests;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static dev.ers.petclinic.ApiPaths.HEALTH;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class HealthTest {
    @Autowired
    private RequestSpecification specification;

    /**
     * Проверяет доступность health endpoint и статус приложения UP.
     */
    @Test
    @DisplayName("Приложение доступно и сообщает статус UP")
    void applicationIsHealthy() {
        given()
                .spec(specification)
                .when()
                .get(HEALTH)
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }
}
