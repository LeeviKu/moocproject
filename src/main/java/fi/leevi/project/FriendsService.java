/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * @author noob9
 */
@Service
public class FriendsService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    FriendsRepository friendsRepository;
    
    public void friendRequests(Model model) {
        UserDetails auth = (UserDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        ArrayList<Friends> list = (ArrayList) friendsRepository.
                findByReciever(userRepository.
                        findByUsername(auth.getUsername()));
        ArrayList<Friends> friendRequests = new ArrayList<>();
        for (Friends friend : list) {
            if (!friend.isFriends()) {
                friendRequests.add(friend);
            }
        }
        model.addAttribute("friendRequests", friendRequests);
    }
    
    public void acceptFriend() {
        
    }
}
