package com.mywallet.api.provider.authentication;

public interface PreAuthorizeProvider {

    /**
     * @since 03/10/2023
     * @implNote modo de uso
     * org.springframework.security.access.prepost.PreAuthorize(PreAuthorizeUtil.IS_AUTHENTICATED)
     * org.springframework.security.access.prepost.PreAuthorize(PreAuthorizeUtil.HAS_ROLE_USER_OR_ADMIN)
     */

    String IS_AUTHENTICATED = "isAuthenticated()";
    String PERMIT_ALL = "permitAll()";
    String HAS_ROLE_USER = "hasRole('ROLE_USER')";
    String HAS_ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
    String HAS_ROLE_USER_OR_ADMIN = "hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')";

}
