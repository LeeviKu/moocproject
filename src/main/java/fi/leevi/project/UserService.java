/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author noob9
 */
@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    
    public void headerLinks(Principal princibal, Model model, User currentUser) {
        if (princibal != null) {
            model.addAttribute("currentUserPath", currentUser.getPath());   
        }
    }
    
    public boolean validateSignup(Model model, String username, String password, String name, String path) {
        model.addAttribute("username", username);
        model.addAttribute("name", name);
        model.addAttribute("path", path);
        
        if(userRepository.findByUsername(username) != null) {
            model.addAttribute("usernameerror", "username already used");
            return false;
        } else if (username.length() < 6 || username.length() > 32) {
            model.addAttribute("usernameerror", "username too short/long");
            return false;
        } else if (username.contains(" ")) {
            model.addAttribute("usernameerror", "no spaces");
            return false;
        }
        
        if(password.length() < 8 || password.length() > 32) {
            model.addAttribute("passworderror", "password too short/long");
            return false;
        } else if (password.contains(" ")) {
            model.addAttribute("passworderror", "no spaces");
            return false;
        }
        
        if(userRepository.findByName(name) != null) {
            model.addAttribute("nameerror", "name already used");
            return false;
        } else if (name.length() < 3 ||name.length() > 12) {
            model.addAttribute("nameerror", "name too short/long");
            return false;
        } else if (name.contains(" ")) {
            model.addAttribute("nameerror", "no spaces");
            return false;
        }
        
        if(userRepository.findByPath(path) != null) {
            model.addAttribute("patherror", "path already used");
            return false;
        } else if (path.length() < 3 || path.length() > 12) {
            model.addAttribute("patherror", "path too short/long");
            return false;
        } else if (!path.matches("[a-zA-Z0-9]*")) {
            model.addAttribute("patherror", "use only letter a-z and numbers");
            return false;
        }
        
        return true;
    }
}
