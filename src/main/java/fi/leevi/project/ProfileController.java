/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author noob9
 */
@Controller
public class ProfileController {
    
    @Autowired
    UserRepository userRepository;
    
    @PostMapping("/profile/{path}")
    public String addProfilePicture(@RequestParam("file") MultipartFile file,
            Principal principal, Model model, @PathVariable String path) throws IOException {
        
        if (file != null) {
            
            if (file.getContentType().equals("image/png")
            || file.getContentType().equals("image/jpg")
            || file.getContentType().equals("image/jpeg")) {
             
            User currentUser = userRepository.findByUsername(principal.getName());
            currentUser.setProfilePicture(file.getBytes());
            userRepository.save(currentUser);
            
            model.addAttribute("person", currentUser);
            }   
        }
 
        return "redirect:/profile/" + path;
    }
    
    @GetMapping("profile/{path}")
    public String profile(@PathVariable String path, Model model, Principal principal) {
        
        if (userRepository.findByPath(path) != null) {
            User profileOwner = userRepository.findByPath(path);
            model.addAttribute("person", profileOwner);
            model.addAttribute("currentUser", userRepository.
                    findByUsername(principal.getName()));
            
            if (profileOwner.getProfilePicture() != null) {
                model.addAttribute("profilepicture", Base64.getEncoder().
                        encodeToString(profileOwner.getProfilePicture()));
            }   
        }
        
        return "profile";
    }
}
