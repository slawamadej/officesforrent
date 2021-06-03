package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.gabinetynagodziny.officesforrent.entity.User;
import pl.gabinetynagodziny.officesforrent.repository.UserRepository;
import pl.gabinetynagodziny.officesforrent.service.SignUpService;

import java.util.Optional;

@Controller
public class SignUpController {

    private SignUpService signUpService;
    private UserRepository userRepository;

    @Autowired
    public SignUpController(SignUpService signUpService, UserRepository userRepository){
        this.signUpService = signUpService;
        this.userRepository = userRepository;
    }

    @GetMapping("/sign_up")
    public ModelAndView signUp(ModelAndView modelAndView){
        modelAndView.setViewName("sign_up");
        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/sign_up")
    public ModelAndView signUpPost(ModelAndView modelAndView, String username, String password, String email){
        User userToSignUp = new User(username, password, email);
        signUpService.signUpUser(userToSignUp);
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/confirm_email")
    public String confirmEmail(String token){
        Optional<User> optionalUser = userRepository.findByToken(token);

        if(!optionalUser.isPresent()){
            return "nie ma tokenu";
        }
        User user = optionalUser.get();
        user.setEnabled(true);
        userRepository.save(user);
        return "Konto aktywowane";
    }

}
