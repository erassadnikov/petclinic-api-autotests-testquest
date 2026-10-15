package dev.ers.petclinic.model;

public record OwnerResponse(Integer id, String firstName, String lastName,
                            String address, String city, String telephone) {
}
