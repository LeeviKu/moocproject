/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    CommentRepository commentRepository;
    
    @GetMapping("/frontpage")
    public String frontpage(Model model, Principal princibal) {
        if (princibal != null) {
            User currentUser = userRepository.findByUsername(princibal.getName());
            model.addAttribute("currentUserPath", currentUser.getPath());
            List<User> friends = friendsService.friends(model, currentUser);
            friends.add(currentUser);
            Pageable pageable = PageRequest.of(0, 25, Sort.by("time").descending());
            List<Post> posts = postRepository.findByUserIn(friends, pageable);
            model.addAttribute("posts", posts);
        }
        return "frontpage";
    }
    
    @PostMapping("/frontpage")
    public String post(Principal principal, @RequestParam String post) {
        System.out.println(post.length());
        if (post.length() <= 254 && !post.isEmpty()) {
            Post newPost = new Post();
            newPost.setPost(post);
            newPost.setUser(userRepository.findByUsername(principal.getName()));
            newPost.setTime(LocalDateTime.now());
            postRepository.save(newPost);
        }
        return "redirect:/frontpage";
    }
    
    @PostMapping("/frontpage/like/{id}")
    public String likePost(@PathVariable Long id, Principal principal) {
        Post post = postRepository.getOne(id);
        User currentUser = userRepository.findByUsername(principal.getName());
        if (!post.getLikes().contains(currentUser)) {
            post.getLikes().add(currentUser);
            postRepository.save(post);
        }
        return "redirect:/frontpage";
    }
    
    @PostMapping("/frontpage/comment/{postid}")
    public String commentPost(@PathVariable Long postid, Principal principal, @RequestParam String postComment) {
        
        if (!postComment.isEmpty()) {
            Post post = postRepository.getOne(postid);
            Comment comment = new Comment();
            comment.setCommentText(postComment);
            comment.setPost(post);
            comment.setUsername(userRepository.findByUsername(principal.getName()).getName());
            comment.setTime(LocalDateTime.now());
            commentRepository.save(comment);
        }
        return "redirect:/frontpage";
    }
}
