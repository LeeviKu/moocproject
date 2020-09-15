/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.security.Principal;
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
public class frontpageController {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PostRepository postRepository;
    
    @GetMapping("/frontpage")
    public String frontpage(Model model, Principal princibal) {
        if (princibal != null) {
            model.addAttribute("currentUserPath", userRepository.findByUsername(princibal.getName()).getPath());   
        }
        
        model.addAttribute("posts", postRepository.findAll());
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
