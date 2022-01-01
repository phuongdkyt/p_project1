package com.example.demo.services;

import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public abstract class JwtService {
//    @Autowired
//    protected JedisPool jedisPool;

    public abstract DecodedJWT authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public abstract Object getClaims(HttpServletRequest request);

    public abstract String findToken(String accountId);

    public abstract DecodedJWT validateJWT(String token);

    public String toToken(String accountId, String token) {
        return accountId + ":" + token;
    }
}
