# petclinic-api-autotests-testquest
Api autotests for project https://github.com/spring-petclinic/spring-petclinic-rest


## Требования для корректного запуска.

- JDK 17
- Maven 3.9.9
- Docker

## Стэк для АТ

- Spring Boot 3.5.7 
- JUnit 5
- RestAssured 
- AssertJ
- Allure Java 2.29.1

## Запуск PetClinic REST

Из корня проекта:

```bash
docker compose pull
docker compose up -d
```

Перед тестами проверьте: `http://127.0.0.1:9966/petclinic/swagger-ui/index.html`

## Запуск тестов

```bash
mvn clean test
```

С другим адресом приложения:

```bash
mvn clean test -DbaseUrl=http://localhost:9966/petclinic
```

## Логи и Allure

После тестов откройте интерактивный отчет:

```bash
mvn allure:serve
```