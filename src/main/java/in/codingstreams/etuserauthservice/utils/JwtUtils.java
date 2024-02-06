package in.codingstreams.etuserauthservice.utils;

import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.UUID;

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
}
