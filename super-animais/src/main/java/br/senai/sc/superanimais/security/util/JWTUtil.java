package br.senai.sc.superanimais.security.util;

import br.senai.sc.superanimais.model.entity.Person;
import br.senai.sc.superanimais.repository.PersonRepository;
import br.senai.sc.superanimais.security.model.PersonDetails;
import br.senai.sc.superanimais.security.repository.PersonDetailsRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Autowired
    private JWTUtil (PersonDetailsRepository pdr) {
        JWTUtil.pdr = pdr;
    }
    private static final String SENHAFORTE = "c127a7b6adb013a5ff879ae71afa62afa4b4ceb72afaa54711dbcde67b6dc325";
    private static PersonDetailsRepository pdr;

    public static String generateToken(PersonDetails person) {
        Algorithm algorithm = Algorithm.HMAC256(SENHAFORTE);
        return JWT.create()
                .withIssuer("DAVELA")
                .withSubject(person.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 1800000))
                .sign(algorithm);
    }

    public static PersonDetails getPerson(String token) {
        String email = JWT.decode(token).getSubject();
        return pdr.findByPerson_Email(email);
    }

}
