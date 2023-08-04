package br.senai.sc.superanimais.security.controller;

import br.senai.sc.superanimais.security.model.Login;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager am;

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody Login login,
                                    HttpServletRequest req,
                                    HttpServletResponse res) {
        SecurityContextRepository scr = new HttpSessionSecurityContextRepository();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
                (login.getEmail(), login.getPassword());
        Authentication authentication = am.authenticate(token);

        if (authentication.isAuthenticated()) {
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            scr.saveContext(securityContext, req, res);
            System.out.println(authentication.getPrincipal());
            return ResponseEntity.ok(authentication.getPrincipal());
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/login")
    private ResponseEntity<?> teste() {
        return ResponseEntity.ok("Teste");
    }

}
