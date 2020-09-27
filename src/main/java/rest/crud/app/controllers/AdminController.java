package rest.crud.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import rest.crud.app.model.User;
import rest.crud.app.repositories.RoleRepository;
import rest.crud.app.service.UserDetailsServiceAdded;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Transactional
//@RequestMapping("/admin")
public class AdminController {

    private UserDetailsServiceAdded userDetailsServiceAdded;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserDetailsServiceAdded(UserDetailsServiceAdded userDetailsServiceAdded) {
        this.userDetailsServiceAdded = userDetailsServiceAdded;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/index")
    public String usersManager(ModelMap model, Principal principal) {
        model.addAttribute("tableHeader", "Admin panel");
        model.addAttribute("allusersheader", "All users");
        model.addAttribute("registrationheader", "Add new user");
        model.addAttribute("user", new User());
        model.addAttribute("allUsersList", userDetailsServiceAdded.getAllUsers());
        model.addAttribute("email", principal.getName() + " with roles: ADMIN USER");
        model.addAttribute("roles", new String[]{"ROLE_USER", "ROLE_ADMIN"});
        return "index";
    }
/*
    @GetMapping(value = "/admin/getOne/{id}")
    public ModelMap editUser(@PathVariable Long id) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("user", userDetailsServiceAdded.findById(id));
        modelMap.toString();
        return modelMap;
    }*/

 /*   @GetMapping(value = "/admin/getOne/{id}")
    //@ResponseBody
    //
    public User getOne(@PathVariable Long id) {
        User user = userDetailsServiceAdded.findById(id);
        System.out.println(user.toString());
        return user;
    }*/

/*    @PostMapping(value = "/admin/edit")
    public String updateUser(@ModelAttribute @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/admin";
        }
        userDetailsServiceAdded.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userDetailsServiceAdded.removeUserById(id);
        return "redirect:/admin";
    }
}*/
}