package pl.gabinetynagodziny.officesforrent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.gabinetynagodziny.officesforrent.entity.User;
import pl.gabinetynagodziny.officesforrent.repository.UserRepository;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public JpaUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //metoda ze springowego interfacu userDetailService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUsername = userRepository.findByUsername(username);

        if(!optionalUsername.isPresent()){
            throw new UsernameNotFoundException("Not found user: " + username);
        }

        return optionalUsername.get();
    }
}
