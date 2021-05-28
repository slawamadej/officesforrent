package pl.gabinetynagodziny.officesforrent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @RequestMapping(value="/user_panel", method=RequestMethod.GET)
    public ModelAndView userPanel(ModelAndView modelAndView){
        //ModelAndView mozemy nazwe widoku jak i obiekty, ktore chcemy przekazac
        modelAndView.setViewName("user_panel");
        return modelAndView;
    }

    @RequestMapping(value="/admin_panel", method=RequestMethod.GET)
    public ModelAndView adminPanel(ModelAndView modelAndView){
        modelAndView.setViewName("admin_panel");
        return modelAndView;
    }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView){
        //ModelAndView mozemy nazwe widoku jak i obiekty, ktore chcemy przekazac
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value="/offices", method=RequestMethod.GET)
    public ModelAndView offices(ModelAndView modelAndView){
        //ModelAndView mozemy nazwe widoku jak i obiekty, ktore chcemy przekazac
        modelAndView.setViewName("offices");
        return modelAndView;
    }
}
