package com.momchil.TU4ALL.utils;

public enum Roles {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ADMIN");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue(){
        return this.value;
    }

    public static Roles getValue(String value) {
        for (Roles role : Roles.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        return null;
    }


}
