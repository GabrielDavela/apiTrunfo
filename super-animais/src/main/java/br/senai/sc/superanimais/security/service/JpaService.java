package br.senai.sc.superanimais.security.service;

import br.senai.sc.superanimais.security.model.PersonDetails;
import br.senai.sc.superanimais.security.repository.PersonDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaService implements UserDetailsService {

    @Autowired
    private PersonDetailsRepository pdr;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return pdr.findByPerson_Email(email);
    }

    public PersonDetails create(PersonDetails pd) {
        return pdr.save(pd);
    }

}
