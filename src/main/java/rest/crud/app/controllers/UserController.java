package rest.crud.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import rest.crud.app.model.User;
import rest.crud.app.service.UserDetailsServiceAdded;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
public class UserController {

    private UserDetailsServiceAdded userDetailsServiceAdded;

    @Autowired
    public void setUserDetailsServiceAdded(UserDetailsServiceAdded userDetailsServiceAdded) {
        this.userDetailsServiceAdded = userDetailsServiceAdded;
    }

    @GetMapping(value = "hello")
    public String printWelcome(ModelMap model, Principal principal) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm your 1st Spring MVC-SECURITY application");
        messages.add("5.2.8 version by aug'20 ");
        messages.add("---------------------------------------------");
        messages.add("Вы вошли под именем: " + principal.getName());
        messages.add("---------------------------------------------");
        messages.add("You're logged as: " + SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("messages", messages);
        return "/hello";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/registration")
    public String newUserReg(ModelMap model) { //@ModelAttribute - to try
        model.addAttribute("header", "User registration");
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping(value = "/save")
    public String saveNewUser(@ModelAttribute("user") @Valid User user,
                              BindingResult result) {
        User existing = userDetailsServiceAdded.findByEmail(user.getUsername());
        if (existing != null){
            result.rejectValue("username", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            return "registration";
        }
        userDetailsServiceAdded.addUser(user);
        return "redirect:/index";
    }

    @GetMapping("/user")
    public String userPage(ModelMap model, Principal principal){
        model.addAttribute("email", principal.getName() + " with roles: USER");
        model.addAttribute("tableHeader", "User information-page");
        model.addAttribute("userheader", "About user");
        model.addAttribute("userinfo", userDetailsServiceAdded.findByEmail(principal.getName()));
        return "user";
    }
}
