/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author noob9
 */
@Controller
public class FriendsController {
    
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/friends")
    public String friendsPage() {
        return "friends";
    }
    
    @PostMapping("/friends")
    public String searchFriend(@RequestParam String name, Model model) {
        System.out.println(name);
        if (userRepository.findByName(name) != null) {
            model.addAttribute("searchresult", userRepository.findByName(name));
            System.out.print(userRepository.findByName(name).getName());
        }
        return "friends";
    }
    
    @PostMapping("/friends/add")
    public String addFriend() {
        System.out.println("added");
        return "redirect:/friends";
    }
}
