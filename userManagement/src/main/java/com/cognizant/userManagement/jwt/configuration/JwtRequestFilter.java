package com.cognizant.userManagement.jwt.configuration;

import com.cognizant.userManagement.service.JwtService;
import com.cognizant.userManagement.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            final String header = request.getHeader("Authorization");
            String jwtToken = null;
            String userName = null;
            if (header != null && header.startsWith("Bearer ")) {
                jwtToken = header.substring(7);
                userName = jwtUtil.getUserNameFromToken(jwtToken);
           }
//            else {
//                System.out.println("JWT token does not start with Bearer");
//            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jwtService.loadUserByUsername(userName);

                if (userDetails != null && jwtUtil.validateToken(jwtToken)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired");
        } catch(MalformedJwtException e){
            System.out.println("Malformed JWT!");
        } catch(SignatureException e){
            System.out.println("Invalid Signature of JWT!");
        }

        filterChain.doFilter(request, response);

    }
}