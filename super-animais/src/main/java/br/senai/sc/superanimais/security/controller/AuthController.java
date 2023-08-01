package br.senai.sc.superanimais.security.controller;

import br.senai.sc.superanimais.security.model.Login;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager am;

//    @PostMapping
//    private ResponseEntity<?> login(@RequestBody Login login,
//                                    HttpServletRequest req) {
//
//    }

}
