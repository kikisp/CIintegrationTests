package com.icebergarts.carwashagent.model;

public enum PermissionsProvider {
    USER_READ("user:read"),
    USER_WRITE("user:write");


    private final String permission;

    PermissionsProvider(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
