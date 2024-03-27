package sg.edu.nus.iss.ibfb4ssfassessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Login;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    // TODO: Task 6
    @GetMapping(path = "/login")
    public ModelAndView login() {

        ModelAndView mav = new ModelAndView("view0");
        Login login = new Login();
        mav.addObject("login", login);
        return mav;
    }

    // TODO: Task 7
    @PostMapping(path = "/login")
    public String processlogin(@ModelAttribute @Valid Login login, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "view0"; // Go back to login page
        } else {
            session.setAttribute("login", login); // To set new session each time you login
            return "view1";
        }
    }

    // For the logout button shown on View 2
    // On logout, session should be cleared
    public String logout() {
        return null;
    }

}
