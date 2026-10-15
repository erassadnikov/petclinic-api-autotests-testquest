package dev.ers.petclinic.model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OwnerResponse(Integer id, String firstName, String lastName,
                            String address, String city, String telephone) {
}
