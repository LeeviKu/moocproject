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
}
