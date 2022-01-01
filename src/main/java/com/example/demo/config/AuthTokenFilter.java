package com.example.demo.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.services.JwtUser;
import com.example.demo.services.UserDetailsServiceImpl;
import com.example.demo.services.UserJwt;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUser jwtUser;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            DecodedJWT decodedJWT = jwtUser.authenticate(httpServletRequest, httpServletResponse);
            if (decodedJWT != null) {
                String username = UserJwt.from(decodedJWT).getUsername();
                if(username == null){
                    username = String.valueOf(decodedJWT.getClaim("id").asString());
                }
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
