/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Lepe
 */
@Controller
public class FrontpageController {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    FriendsRepository friendsRepository;
    
    @Autowired
    FriendsService friendsService;
    
    @GetMapping("/frontpage")
    public String frontpage(Model model, Principal princibal) {
        if (princibal != null) {
            User currentUser = userRepository.findByUsername(princibal.getName());
            model.addAttribute("currentUserPath", currentUser.getPath());
            List<User> friends = friendsService.friends(model, currentUser);
            friends.add(currentUser);
            List<Post> posts = postRepository.findByUserIn(friends);
            model.addAttribute("posts", posts);
        }
        return "frontpage";
    }
    
    @PostMapping("/frontpage")
    public String post(Principal principal, @RequestParam String post) {
        Post newPost = new Post();
        newPost.setPost(post);
        newPost.setUser(userRepository.findByUsername(principal.getName()));
        postRepository.save(newPost);
        return "redirect:/frontpage";
    }
}