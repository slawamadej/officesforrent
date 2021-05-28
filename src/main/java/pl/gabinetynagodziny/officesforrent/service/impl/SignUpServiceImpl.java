package pl.gabinetynagodziny.officesforrent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.gabinetynagodziny.officesforrent.component.mailer.RandomStringFactory;
import pl.gabinetynagodziny.officesforrent.component.mailer.SignUpMailer;
import pl.gabinetynagodziny.officesforrent.entity.User;
import pl.gabinetynagodziny.officesforrent.repository.UserRepository;
import pl.gabinetynagodziny.officesforrent.service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {

    private static final int TOKEN_LENGTH = 15;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private SignUpMailer signUpMailer;

    @Autowired
    public SignUpServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, SignUpMailer signUpMailer){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.signUpMailer = signUpMailer;
    }

    @Override
    public User signUpUser(User user) {
        Assert.isNull(user.getUserId(), "User exists");
        //czy asserty w kodzie

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String token = RandomStringFactory.getRandomString(TOKEN_LENGTH);
        user.setToken(token);
        User userSaved = userRepository.save(user);
        signUpMailer.sendConfirmationLink(userSaved.getEmail(), token);
        return userSaved;
    }


}
