package com.mywallet.api.provider.authentication;


import com.mywallet.api.domain.enums.RolePersonEnum;
import com.mywallet.api.provider.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import static com.mywallet.api.provider.constrans.Environment.BEARER_TYPE;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticationFilterBeforeAfter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        final var tokenJWT = getToken(request);

        final var context = SecurityContextHolder.getContext();

        if (StringUtils.hasText(tokenJWT) && ObjectUtils.isEmpty(context.getAuthentication())) {
            var isValidToken = JwtProvider.isValid(tokenJWT);

            if (isValidToken) {
                final var userDetails = getUserDetails(tokenJWT);

                final var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                final var webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);

                authenticationToken.setDetails(webAuthenticationDetails);

                context.setAuthentication(authenticationToken);
            }
        }

        setAttributeIdentifier(request, context);

        filterChain.doFilter(request, response);
    }

    @AfterReturning(value = "@annotation(org.springframework.security.access.prepost.PreAuthorize)", returning = "returned")
    public void afterReturningPreAuthorize(final Object returned) {
        final var userContext = getUserSecurityContextHolder();

        if (Objects.nonNull(userContext)) {
            final var isAdmin = userContext.getAuthorities()
                    .stream()
                    .anyMatch(granted -> RolePersonEnum.ADMIN.getType().equals(granted.getAuthority()));

            if (isAdmin) return;

        }
    }

    private String getToken(HttpServletRequest request) {

        final String token = request.getHeader("Authorization");
        if (isEmpty(token) || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(BEARER_TYPE.length());
    }

    private UserDetails getUserDetails(final String tokenJwt) {
        final var value = JwtProvider.decode(tokenJwt)
                .orElseThrow(() -> new RuntimeException("Erro ao realizar o decode do token"));

        return new User("USER",
                "",
                List.of(new SimpleGrantedAuthority(RolePersonEnum.USER.getType())),
                Integer.valueOf(value.getIdUsuario()));
    }

    private void setAttributeIdentifier(final HttpServletRequest request, final SecurityContext securityContext) {
        final BiFunction<String, Object, String> format = MessageFormat::format;
        final var authentication = securityContext.getAuthentication();

        final var identifier = ObjectUtils.isEmpty(authentication) ?
                format.apply("IP:{0}", request.getRemoteAddr()) :
                format.apply("USER:{0}", authentication.getName());

        final var requestAttributes = RequestContextHolder.currentRequestAttributes();
        requestAttributes.setAttribute("IDENTIFIER", identifier, RequestAttributes.SCOPE_REQUEST);
    }

    public static User getUserSecurityContextHolder() {
        final var context = SecurityContextHolder.getContext();
        if (Objects.nonNull(context) &&
                Objects.nonNull(context.getAuthentication()) &&
                context.getAuthentication().getPrincipal() instanceof User userAuth) return userAuth;
        return null;
    }
}
