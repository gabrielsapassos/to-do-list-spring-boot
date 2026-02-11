package com.gabrielsantana.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.gabrielsantana.todolist.user.User;
import com.gabrielsantana.todolist.user.UserService;
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

    private final UserService userService;

    public FilterTaskAuth(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // If the request is a POST of a new user, let it pass right away
        String path = request.getServletPath();
        if (path.equals("/api/v1/users") && request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Gets the username and password fields in the request
        String auth = request.getHeader("Authorization");

        // Remove the "Basic" part and creates a new String
        // Ex: "Basic dXNlcjoxMjM0NDU2Nzc=" -> "dXNlcjoxMjM0NDU2Nzc="
        String authEncoded = auth.substring(6).trim();

        // Transforms the Byte64 into an Array of the byte type
        byte[] authdecoded = Base64.getDecoder().decode(authEncoded);

        // Transforms the byte array into a String. Now we have a String like this: username:password
        String authString = new String(authdecoded);

        // Separate the user username and the password
        String[] credentials = authString.split(":");

        String username = credentials[0];
        String password = credentials[1];

        User user;

        try {
            user = userService.findEntityByUsername(username);
        } catch (Exception e) {
            // Verify if the username exists by a method in the User service layer
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"User not found\"}");
            return;
        }

        // Verify if the password matches the hashed password in th database
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if (!result.verified) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Invalid password\"}");
            return;
        }

        // If the user reaches here, the filter authorize the current user, going to the controller layer after this
        filterChain.doFilter(request, response);
    }
}
