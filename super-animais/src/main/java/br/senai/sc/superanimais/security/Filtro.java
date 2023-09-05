package br.senai.sc.superanimais.security;

import br.senai.sc.superanimais.security.model.PersonDetails;
import br.senai.sc.superanimais.security.util.CookieUtil;
import br.senai.sc.superanimais.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class Filtro extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        if (rotaPrivada(request.getRequestURL().toString())) {
            try {
                String token = CookieUtil.getToken(request);
                PersonDetails person = JWTUtil.getPerson(token);
                response.addCookie(CookieUtil.generateCookie(person));

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        person.getUsername(),
                        null,
                        person.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean rotaPrivada(String requestURL) {
        System.out.println(requestURL);
        return requestURL.contains("/card") || requestURL.contains("/person");
    }
}
