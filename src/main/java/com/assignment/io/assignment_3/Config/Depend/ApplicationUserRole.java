package com.assignment.io.assignment_3.Config.Depend;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


public enum ApplicationUserRole {
    USER(Sets.newHashSet(ApplicationUserPermission.READ)),

    ADMIN(Sets.newHashSet(ApplicationUserPermission.READ, ApplicationUserPermission.WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }


    public Set<String> getGrantedAuthorities() {
        Set<String> permissions = getPermissions().stream()
                .map(permission ->permission.getPermission())
                .collect(Collectors.toSet());
        return permissions;
    }

}
