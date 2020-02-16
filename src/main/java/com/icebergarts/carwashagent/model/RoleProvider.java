package com.icebergarts.carwashagent.model;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.icebergarts.carwashagent.model.PermissionsProvider.*;

public enum RoleProvider {
    USER(Sets.newHashSet(USER_READ)),
    ADMIN(Sets.newHashSet(USER_READ,USER_WRITE)),
    CLIENT(Sets.newHashSet(USER_READ));

    private final Set<PermissionsProvider> permissions;

    RoleProvider(Set<PermissionsProvider> permissions) {
        this.permissions = permissions;
    }

    public Set<PermissionsProvider> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissionsList = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissionsList.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissionsList;
    }
}
