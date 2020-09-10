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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    
    @Autowired
    FriendsRepository friendsRepository;
    
    @Autowired
    FriendsService friendsService;
    
    @Autowired
    UserService userService;
    
    @GetMapping("/friends")
    public String friendsPage(Model model, Principal principal) {
        userService.headerLinks(principal, model);
        friendsService.friendRequests(model);
        friendsService.friends(model);
        return "friends";
    }
    
    @PostMapping("/friends")
    public String searchFriend(@RequestParam String name, Model model) {
        System.out.println(name);
        if (userRepository.findByName(name) != null) {
            // validate that retunrns boolean that indicates can you add or no
            model.addAttribute("searchresult", userRepository.findByName(name));
        }
        friendsService.friendRequests(model);
        friendsService.friends(model);
        return "friends";
    }
    
    @PostMapping("/friends/add/{name}")
    public String addFriend(@PathVariable String name) {
        UserDetails auth = (UserDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        Friends friend = new Friends();
        User reciever = userRepository.findByName(name);
        User sender = userRepository.findByUsername(auth.getUsername());
        friend.setReciever(reciever);
        friend.setSender(sender);
        //validate so that you cant add same friend through link 
        friendsRepository.save(friend);
        
        return "redirect:/friends";
    }
    
    @PostMapping("/friends/accept/{name}")
    public String acceptFriend(@PathVariable String name) {
        UserDetails auth = (UserDetails) SecurityContextHolder.
        getContext().getAuthentication().getPrincipal();
        friendsService.acceptFriend(name, auth);
        return "redirect:/friends";
    }
}
