/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author noob9
 */
@Controller
public class FriendsController {
    
    @GetMapping("friends")
    public String friendsPage() {
        return "friends";
    } 
}
