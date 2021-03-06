/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.security.Principal;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author noob9
 */
@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    UserService userService;
    
    @GetMapping("/signup")
    public String list(Model model) {
        model.addAttribute("accounts", userRepository.findAll());
        return "signup";
    }

    @PostMapping("/signup")
    public String add(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String path, Model model) {
        if (!userService.validateSignup(model, username, password, name, path)) {
            return "signup";
        }
        User user = new User(username, passwordEncoder.encode(password), name,
                path);
        
        userRepository.save(user);
        return "redirect:/login";
    }
    
    @GetMapping("login")
    public String loginPage() {
        return "customlogin";
    }

}