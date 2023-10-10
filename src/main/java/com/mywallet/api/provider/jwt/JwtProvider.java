package com.mywallet.api.provider.jwt;

import com.mywallet.api.provider.Date;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.StringJoiner;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class JwtProvider implements Serializable {

    private JwtProvider() { }

    public static final LocalDateTime JWT_TOKEN_VALIDITY = now().plusHours(3);
    public static final String SECRET = "bGliZXJkYWRlX2ZpbmFuY2VpcmFfdHJhbnNwYXJlbmNpYV9nZXJhX2NvbmZpYW5jYQ==";

    public static Optional<String> gerar(
            final Integer idUsuario,
            final Long idPerfil,
            final LocalDateTime dataExpiracao
    ) {
        checkGerar(idUsuario, idPerfil, dataExpiracao);
        return Optional.ofNullable(Jwts.builder()
                .setSubject(createSubject(idUsuario, idPerfil))
                .setExpiration(Date.toDate(dataExpiracao))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact());
    }

    public static Optional<String> gerar(final Integer idUsuario, final Long idPerfil) {
        checkGerar(idUsuario, idPerfil);
        return Optional.ofNullable(Jwts.builder()
                .setSubject(createSubject(idUsuario, idPerfil))
                .setExpiration(Date.toDate(JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact());
    }

    public static Boolean isValid(final String token) {
        try {
            if (StringUtils.isBlank(token)) {
                return false;
            }
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String getUserId(final String token) {
        try {
            String subject = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
            if (StringUtils.isBlank(subject)) {
                throw new RuntimeException("Não foi possivei recuperar informações do token!");
            }
            String[] decode = subject.split(";");
            return String.valueOf(decode[0]);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possivei recuperar informações do token! " + e.getMessage());
        }
    }

    public static String getUserId() {
        try {
            final HttpServletRequest request = getCurrentHttpRequest();
            final String possivelToken = request.getHeader("Authorization");
            if (isEmpty(possivelToken) || !possivelToken.startsWith("Bearer ")) {
                throw new RuntimeException("Não foi possivei recuperar informações do token!");
            }
            final String token = possivelToken.substring(7);
            String subject = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
            if (StringUtils.isBlank(subject)) {
                throw new RuntimeException("Não foi possivei recuperar informações do token!");
            }
            String[] decode = subject.split(";");
            return String.valueOf(decode[0]);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possivei recuperar informações do token! " + e.getMessage());
        }

    }

    public static Optional<Value> decode(final String token) {
        if (StringUtils.isBlank(token)) {
            return Optional.empty();
        } else {
            try {
                String subject = ((Claims) Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody()).getSubject();
                if (StringUtils.isBlank(subject)) {
                    return Optional.empty();
                } else {
                    String[] decode = subject.split(";");
                    return ArrayUtils.getLength(decode)
                            != 3 ? Optional.empty()
                            : Optional.of(Value.newInstance(String.valueOf(decode[0]), Long.valueOf(decode[1])));
                }
            } catch (ExpiredJwtException var3) {
                return Optional.of(Value.newInstance("ACESSO EXPIROU - NECESSARIO NOVA AUTENTICACAO"));
            } catch (UnsupportedJwtException var4) {
                return Optional.empty();
            } catch (IllegalArgumentException | MalformedJwtException var5) {
                return Optional.of(Value.newInstance("TOKEN INVALIDO - CONTATE O DEPTO DE TI"));
            }
        }

    }


    public interface Value {
        String getIdUsuario();

        Long getIdPerfil();

        String getMsgInconsistencia();

        static Value newInstance(final String idUsuario, final Long idPerfil, final String msgInconsistencia) {
            return new Value() {
                public String getIdUsuario() {
                    return idUsuario;
                }

                public Long getIdPerfil() {
                    return idPerfil;
                }

                public String getMsgInconsistencia() {
                    return msgInconsistencia;
                }
            };
        }

        static Value newInstance(final String idUsuario, final Long idPerfil) {
            return newInstance(idUsuario, idPerfil, (String) null);
        }

        static Value newInstance(final String mensagem) {
            return newInstance((String) null, (Long) null, mensagem);
        }
    }

    private static HttpServletRequest getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .orElseThrow(() -> new RuntimeException("Não foi possivel recuperar request"));
    }

    private static String createSubject(final Integer idUsuario, final Long idPerfil) {
        return String.format("%s;%s;%s", idUsuario, idPerfil, LocalDate.now());
    }

    private static void checkGerar(final Integer idUsuario, final Long idPerfil, final LocalDateTime dataExpiracao) {
        StringJoiner check = new StringJoiner(" ");
        if (idUsuario == null) {
            check.add("[ idUsuario ]");
        }

        if (idPerfil == null) {
            check.add("[ idPerfil ]");
        }

        if (!Date.isValida(dataExpiracao)) {
            check.add("[ dataExpiracao ]");
        }

        if (check.length() > 0) {
            throw new IllegalArgumentException(String.format("Faltou definir arg(s): %s para gerar Token", check));
        }
    }

    private static void checkGerar(final Integer idUsuario, final Long idPerfil) {
        StringJoiner check = new StringJoiner(" ");
        if (idUsuario == null) {
            check.add("[ idUsuario ]");
        }

        if (idPerfil == null) {
            check.add("[ idPerfil ]");
        }

        if (check.length() > 0) {
            throw new IllegalArgumentException(String.format("Faltou definir arg(s): %s para gerar Token", check));
        }
    }
}
