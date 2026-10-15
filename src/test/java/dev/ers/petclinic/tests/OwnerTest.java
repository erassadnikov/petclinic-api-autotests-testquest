package dev.ers.petclinic.tests;

import dev.ers.petclinic.client.OwnerClient;
import dev.ers.petclinic.model.OwnerRequest;
import dev.ers.petclinic.model.OwnerResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static dev.ers.petclinic.assertions.OwnerAssertions.assertOwner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OwnerTest {
    @Autowired
    private OwnerClient owners;

    private Integer ownerId;

    /** Корнер кейс для случая падения теста: удаляет оставшуюся тестовую запись, если CRUD-сценарий не завершился. */
    @AfterEach
    void deleteCreatedOwner() {
        if (ownerId != null) {
            assertThat(owners.delete(ownerId).statusCode())
                    .as("Удаление тестового owner %s", ownerId)
                    .isIn(204, 404);
        }
    }

    /** Жизненный цикл API owner, е2е как по ТЗ */
    @Test
    @DisplayName("Owner можно создать, получить, обновить и удалить")
    void ownerLifecycle() {
        // Создаем owner и сохраняем id для последующих запросов и аварийной очистки.
        OwnerRequest original = new OwnerRequest(
                "Test", "Owner", "123 Test Street", "London", "1234567890");
        OwnerResponse created = owners.create(original)
                .then()
                .statusCode(201)
                .extract()
                .as(OwnerResponse.class);
        ownerId = created.id();
        assertThat(ownerId)
                .isPositive();
        assertOwner(created, ownerId, original);

        // Читаем запись по id и проверяем, что исходные данные сохранились.
        OwnerResponse fetched = owners.get(ownerId)
                .then()
                .statusCode(200)
                .extract()
                .as(OwnerResponse.class);
        assertOwner(fetched, ownerId, original);

        // Обновляем данные и отдельным GET подтверждаем сохранение изменений.
        OwnerRequest updated = new OwnerRequest(
                "Updated", "Owner", "456 Updated Street", "Oxford", "0987654321");
        owners.update(ownerId, updated)
                .then()
                .statusCode(204);
        OwnerResponse afterUpdate = owners.get(ownerId)
                .then()
                .statusCode(200)
                .extract()
                .as(OwnerResponse.class);
        assertOwner(afterUpdate, ownerId, updated);

        // Удаляем запись и проверяем, что она больше недоступна по id.
        owners.delete(ownerId)
                .then()
                .statusCode(204);
        owners.get(ownerId)
                .then()
                .statusCode(404);

        // Удаление подтверждено: повторная очистка в @AfterEach не нужна.
        ownerId = null;
    }

    /** Проверяет статус и ошибки валидации для пустых обязательных полей. */
    @Test
    @DisplayName("Пустые обязательные поля owner возвращают ошибку валидации")
    void emptyRequiredFieldsAreRejected() {
        owners.create(new OwnerRequest("", "", "", "", ""))
                .then()
                .statusCode(400)
                .contentType("application/problem+json")
                .body("status", equalTo(400))
                .body("title", equalTo("MethodArgumentNotValidException"))
                .body("detail", equalTo("The request contains invalid or missing parameters"))
                .body("schemaValidationErrors.field",
                        hasItems("firstName", "lastName", "address", "city", "telephone"))
                .body("schemaValidationErrors.message", everyItem(not(blankOrNullString())))
                .body("schemaValidationErrors.rejectedValue", everyItem(equalTo("")));
    }
}
