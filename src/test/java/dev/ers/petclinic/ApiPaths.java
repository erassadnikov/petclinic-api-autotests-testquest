package dev.ers.petclinic;

/** Константы для урлов */
public final class ApiPaths {

    public static final String HEALTH = "/actuator/health";
    public static final String OWNERS = "/api/owners";
    public static final String OWNER_BY_ID = OWNERS + "/{id}";

}