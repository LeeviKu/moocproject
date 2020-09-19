/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author noob9
 */
@Controller
public class ProfileController {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    SkillRepository skillRepository;
    
    @Autowired
    UserService userService;
    
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
            User currentUser = userRepository.findByUsername(principal.getName());
            userService.headerLinks(principal, model, currentUser);
            model.addAttribute("person", profileOwner);
            model.addAttribute("currentUser", currentUser);
            List<Skill> skills = new ArrayList<>(skillRepository.findByUser(profileOwner));
            Collections.sort(skills, (a, b) -> {
                return b.likes.size() - a.likes.size();
            });
            
            List<Skill> top3Skills = new ArrayList<>();
            if (skills.size() > 3) {
                top3Skills = skills.subList(0, 3);
                model.addAttribute("otherskills", skills.subList(3, skills.size()));
            } else if (skills.size() <= 3) {
                top3Skills = skills.subList(0, skills.size());
            }
            
            model.addAttribute("top3skills", top3Skills);
            
            if (profileOwner.getProfilePicture() != null) {
                model.addAttribute("profilepicture", Base64.getEncoder().
                        encodeToString(profileOwner.getProfilePicture()));
            }   
        }
        
        return "profile";
    }
    
    
    @PostMapping("profile/addskill/{path}")
    public String addSkill(@PathVariable String path, Principal principal, Model model, @RequestParam String skill) {
        User currentUser = userRepository.findByUsername(principal.getName());
        if (skillRepository.findBySkillAndUser(skill, currentUser) == null) {
            Skill newSkill = new Skill();
            newSkill.setUser(currentUser);
            newSkill.setSkill(skill);
            skillRepository.save(newSkill);
        }
        return "redirect:/profile/" + path;
    }
    
    @PostMapping("/profile/{path}/like/{id}")
    public String likePost(@PathVariable Long id, @PathVariable String path, Principal principal) {
        Skill skill = skillRepository.getOne(id);
        User currentUser = userRepository.findByUsername(principal.getName());
        if (!skill.getLikes().contains(currentUser)) {
            skill.getLikes().add(currentUser);
            skillRepository.save(skill);
        }
        return "redirect:/profile/" + path;
    }
}
