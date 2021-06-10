package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.gabinetynagodziny.officesforrent.entity.User;
import pl.gabinetynagodziny.officesforrent.repository.UserRepository;
import pl.gabinetynagodziny.officesforrent.service.UserService;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class SignUpController {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public SignUpController(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/sign_up")
    public ModelAndView signUp(ModelAndView modelAndView){
        modelAndView.setViewName("sign_up");
        return modelAndView;
    }

    @PostMapping("/sign_up")
    public String signUpPost(Model model, String username, String password, String email){
        User userToSignUp = new User();
        userToSignUp.setPassword(password);
        userToSignUp.setUsername(username);
        userToSignUp.setEmail(email);
        userToSignUp.setRoles(new ArrayList<>());
        userService.signUpUser(userToSignUp);
        model.addAttribute("signUpRedirect",true);
        return "login";
    }

    @ResponseBody
    @GetMapping("/confirm_email")
    public String confirmEmail(@RequestParam("token") String token){
        System.out.println("TOKEN: " + token);
        Optional<User> optionalUser = userRepository.findByToken(token);

        if(optionalUser.isEmpty()){
            return "nie ma tokenu";
        }
        User user = optionalUser.get();
        user.setEnabled(true);
        userRepository.save(user);
        return "Konto aktywowane";
    }

}
