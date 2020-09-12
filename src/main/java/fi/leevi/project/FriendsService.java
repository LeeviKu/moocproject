/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.util.ArrayList;
import java.util.List;
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
    
    public List<Friends> friendRequests(Model model, User currentUser) {
        List<Friends> list = friendsRepository.
                findByReciever(currentUser);
        List<Friends> friendRequests = new ArrayList<>();
        for (Friends friend : list) {
            if (!friend.isFriends()) {
                friendRequests.add(friend);
            }
        }
        model.addAttribute("friendRequests", friendRequests);
        return friendRequests;
    }
    
    public void acceptFriend(String name, User currentUser) {
       ArrayList<Friends> list = (ArrayList) friendsRepository.
               findByReciever(currentUser);
       User whoToAccept = userRepository.findByName(name);
       
       for (Friends friend : list) {
           if (friend.getSender() == whoToAccept) {
               friend.setFriends(true);
               friendsRepository.save(friend);
           }
       }
    }
    
    public List<User> friends(Model model, User currentUser) {
        ArrayList<Friends> list = (ArrayList) friendsRepository.
                findByReciever(currentUser);
        ArrayList<Friends> list2 = (ArrayList) friendsRepository.
                findBySender(currentUser);
        ArrayList<User> friends = new ArrayList<>();
        
        for (Friends friend : list) {
            if (friend.isFriends()) {
                friends.add(friend.getSender());
            }
        }
        
        for (Friends friend : list2) {
            if (friend.isFriends()) {
                friends.add(friend.getReciever());
            }
        }
        model.addAttribute("friends", friends);
        return friends;
    }
    
    public boolean validateSearch(User searchedUser, User currentUser,
            List<User> friends) {
        if (searchedUser == currentUser) {
            return false;
        }
        
        for (User user : friends) {
            if (searchedUser == user) {
                return false;
            }
        }
        
        return true;
    }
}
