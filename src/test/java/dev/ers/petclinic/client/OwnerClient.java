package dev.ers.petclinic.client;

import dev.ers.petclinic.model.OwnerRequest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static dev.ers.petclinic.ApiPaths.OWNERS;
import static dev.ers.petclinic.ApiPaths.OWNER_BY_ID;
import static io.restassured.RestAssured.given;

public class OwnerClient {

    private final RequestSpecification specification;

    public OwnerClient(RequestSpecification specification) {
        this.specification = specification;
    }

    public Response create(OwnerRequest owner) {
        return given()
                .spec(specification)
                .contentType(ContentType.JSON)
                .body(owner)
                .post(OWNERS);
    }

    public Response get(int id) {
        return given()
                .spec(specification)
                .get(OWNER_BY_ID, id);
    }

    public Response update(int id, OwnerRequest owner) {
        return given()
                .spec(specification)
                .contentType(ContentType.JSON)
                .body(owner)
                .put(OWNER_BY_ID, id);
    }

    public Response delete(int id) {
        return given()
                .spec(specification)
                .delete(OWNER_BY_ID, id);
    }
}
