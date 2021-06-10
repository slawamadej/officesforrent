package pl.gabinetynagodziny.officesforrent.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.gabinetynagodziny.officesforrent.entity.Role;
import pl.gabinetynagodziny.officesforrent.provider.mailer.RandomStringFactory;
import pl.gabinetynagodziny.officesforrent.provider.mailer.SignUpMailer;
import pl.gabinetynagodziny.officesforrent.entity.User;
import pl.gabinetynagodziny.officesforrent.repository.RoleRepository;
import pl.gabinetynagodziny.officesforrent.repository.UserRepository;
import pl.gabinetynagodziny.officesforrent.service.UserService;
import pl.gabinetynagodziny.officesforrent.util.Constans;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final int TOKEN_LENGTH = 15;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SignUpMailer signUpMailer;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, SignUpMailer signUpMailer
    ,RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.signUpMailer = signUpMailer;
        this.roleRepository = roleRepository;
    }

    @Override
    public User signUpUser(User user) {
        Assert.isNull(user.getUserId(), "User exists");
        //czy asserty w kodzie

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String token = RandomStringFactory.getRandomString(TOKEN_LENGTH);
        user.setToken(token);
        Optional<Role> roleOptional = roleRepository.findByName(Constans.USER);
        if(roleOptional.isPresent()){
            user.getRoles().add(roleOptional.get());
        }
        User userSaved = userRepository.save(user);
        signUpMailer.sendConfirmationLink(userSaved.getEmail(), token);
        return userSaved;
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
