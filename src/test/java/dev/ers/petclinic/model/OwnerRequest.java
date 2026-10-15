package dev.ers.petclinic.model;

public record OwnerRequest(String firstName, String lastName, String address,
                           String city, String telephone) {
}
