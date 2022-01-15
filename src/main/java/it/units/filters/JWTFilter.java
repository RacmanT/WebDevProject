package it.units.filters;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import it.units.api.TripResource;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//@WebFilter(asyncSupported = true, urlPatterns = {"/api/*"})
@WebFilter("/api/*")
public class JWTFilter implements Filter {

    private static final Logger log = Logger.getLogger(JWTFilter.class.toString());
    private final String excludedPath = "/api/register";

    public JWTFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = ((HttpServletRequest) request).getRequestURI();
                 /*
                   try {
                   request.setAttribute("userID", "testUserID");
            chain.doFilter(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
            log.severe("ERROR: " + e.getMessage());
        }*/

        try {
            if (excludedPath.equals(path)) {
                chain.doFilter(request, response);
                return;
            }

            String jwtString = null;
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer")) {
                jwtString = authHeader.substring(7);
            }
            if (jwtString != null) {

                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(jwtString);
                //String userID = FirebaseAuth.getInstance().getUser(decodedToken.getUid()).getUid();
                String userID = decodedToken.getUid();

                userID = "testUserID";
                request.setAttribute("userID", userID);
                chain.doFilter(request, response);
            } else {
                log.info("Request without token JWT");
                response.sendError(400, "Not authorized!");
            }
        } catch (FirebaseAuthException | IOException | ServletException e) {
            log.severe("Error at decoding JWT token: " + e.getMessage());
            response.sendError(400, "Something is wrong with the JWT token!");
        }
    }

    @Override
    public void destroy() {
    }

}
