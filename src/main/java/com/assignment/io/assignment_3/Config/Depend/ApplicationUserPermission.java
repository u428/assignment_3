package com.assignment.io.assignment_3.Config.Depend;

public enum ApplicationUserPermission {
    READ("read"),
    WRITE("write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
