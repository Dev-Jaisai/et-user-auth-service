package in.codingstreams.etuserauthservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static in.codingstreams.etuserauthservice.constant.LoggingConstant.*;

@Slf4j
public class JwtUtils {
    private JwtUtils() {
    }

    private static final SecretKey SECRETE_KEY = Jwts.SIG.HS256.key().build();
    private static final String ET_USER_AUTH_SERVICE = "ET_USER_AUTH_SERVICE";

    public static String generateAccessToken(String email) {
        var issueAt = new Date();
        var expiration = DateUtils.addMinutes(issueAt, 15);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ET_USER_AUTH_SERVICE)
                .issuedAt(issueAt)
                .expiration(expiration)
                .subject(email)
                .signWith(SECRETE_KEY)
                .compact();
    }

    public static boolean isValidToken(String accessToken) {
        return parseToken(accessToken).isPresent();
    }

    private static Optional<Claims> parseToken(String accessToken) {
        log.info(METHOD_LOG_START, "parseToken");
        var jwtParser = Jwts.parser()
                .verifyWith(SECRETE_KEY)
                .build();

        try {
            log.info(METHOD_LOG_END, "parseToken");
            return Optional.of(jwtParser.parseSignedClaims(accessToken).getPayload());
        } catch (JwtException | IllegalArgumentException e) {
            log.error(METHOD_LOG_ERROR, "parseToken", "JwtException");
            return Optional.empty();
        }
    }

    public static Optional<String> getUsernameFromToken(String accessToken) {
        return parseToken(accessToken)
                .map(Claims::getSubject);
    }

}
