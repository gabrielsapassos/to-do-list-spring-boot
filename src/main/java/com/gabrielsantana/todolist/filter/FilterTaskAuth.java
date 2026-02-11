package com.gabrielsantana.todolist.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Gets the username and password fields in the request
        String auth = request.getHeader("Authorization");
        System.out.println(auth);

        // Remove the "Basic" part and creates a new String
        // Ex: "Basic dXNlcjoxMjM0NDU2Nzc=" -> "dXNlcjoxMjM0NDU2Nzc="
        String authEncoded = auth.substring(6).trim();
        System.out.println(authEncoded);

        // Transforms the Byte64 into an Array of the byte type
        byte[] authdecoded = Base64.getDecoder().decode(authEncoded);
        System.out.println(authEncoded);

        // Transforms the byte array into a String. Now we have a String like this: username:password
        String authString = new String(authdecoded);
        System.out.println(authString);

        // Separate the user username and the password
        String[] credentials = authString.split(":");

        String username = credentials[0];
        String password = credentials[1];

        System.out.println(username);
        System.out.println(password);

        filterChain.doFilter(request, response);
    }
}
