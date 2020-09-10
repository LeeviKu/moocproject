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
import org.springframework.web.bind.annotation.PathVariable;
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
        }
        return "friends";
    }
    
    @PostMapping("/friends/add/{name}")
    public String addFriend(@PathVariable String name) {
        System.out.println(name);
        return "redirect:/friends";
    }
}
