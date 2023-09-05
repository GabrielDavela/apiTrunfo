package br.senai.sc.superanimais.security.util;

import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.security.model.PersonDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.WebUtils;

public class CookieUtil {

    public static Cookie generateCookie(PersonDetails person) {
        String token = JWTUtil.generateToken(person);
        Cookie cookie = new Cookie("Token", token);
        cookie.setPath("/");
        cookie.setMaxAge(1800);
        return cookie;
    }

    public static String getToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "Token");
        if (cookie != null) {
            return cookie.getValue();
        }
        throw new RuntimeException("Cookie não encontrado!");
    }

}
