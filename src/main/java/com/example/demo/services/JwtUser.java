package com.example.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

@Service
//@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JwtUser extends JwtService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

//    @Value("${app.jwt.ttl}")
//    private long ttlJwt;

    @Autowired
    public JwtUser(@Value("${app.jwt.secret.user}") String secretJwt) {
        this.algorithm = Algorithm.HMAC256(secretJwt);
        this.jwtVerifier = JWT.require(this.algorithm).build();
    }


    @Override
    public DecodedJWT authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = getJWTToken(request);
        if (accessToken == null) {
            return null;
        }

        return validateJWT(accessToken);
    }

    @Override
    public Object getClaims(HttpServletRequest request) {
        return null;
    }

    @Override
    public String findToken(String accountId) {
        return null;
    }

    @Override
    public DecodedJWT validateJWT(String token) {
        return this.jwtVerifier.verify(token);
    }

    protected String getJWTToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    public String generateJWT(UserJwt user) {
        return JWT.create()
                .withIssuedAt(Date.from(Instant.now()))
                .withClaim("username", user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .withExpiresAt(new Date(System.currentTimeMillis() + 300 * 60 * 1000))
//                .withIssuer(request.getRequestURL().toString())
                .sign(this.algorithm);
    }
    public String createToken(Authentication authentication) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return JWT.create()
                .withIssuedAt(Date.from(Instant.now()))
                .withClaim("id", String.valueOf(user.getAttributes().get("id")))
                .withExpiresAt(new Date(System.currentTimeMillis() + 300 * 60 * 1000))
//                .withIssuer(request.getRequestURL().toString())
                .sign(this.algorithm);
    }
}
