package bloggy.config;

import bloggy.dtos.UserDto;
import bloggy.services.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
    public class UserAuthProvider {

        @Value("${security.jwt.token.secret-key}")
        private String secretKey;

        private final UserService userService;

        @PostConstruct
        protected void init() {
            secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        }

        public String createToken(String login) {
            Date now = new Date();
            Date validity = new Date(now.getTime() + 3600000);
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withSubject(login)
                    .withIssuedAt(now)
                    .withExpiresAt(validity)
                    .sign(algorithm);
        }

        public Authentication validateToken(String token) {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            System.out.println(token);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();

            DecodedJWT decoded = verifier.verify(token);
            System.out.println(decoded);
            System.out.println(decoded.getToken());
            String issuer = decoded.getIssuer();

            // To get the expiration time:
            Date expiresAt = decoded.getExpiresAt();
            UserDto user = userService.findByEmail(decoded.getSubject());

            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        }

    }
