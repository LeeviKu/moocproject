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
        List<Friends> list2 = friendsRepository.
                findBySender(currentUser);
        
        List<Friends> recievedFriendRequests = new ArrayList<>();
        List<Friends> allFriendRequests = new ArrayList<>();
        
        for (Friends friend : list) {
            if (!friend.isFriends()) {
                recievedFriendRequests.add(friend);
                allFriendRequests.add(friend);
            }
        }
        // adds all recieved friendrequests to model
        model.addAttribute("friendRequests", recievedFriendRequests);
        
        for (Friends friend : list2) {
            if (!friend.isFriends()) {
                allFriendRequests.add(friend);
            }
        }
        // returns all friendrequests
        return allFriendRequests;
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
    
    public void rejectFriend(String name, User currentUser) {
        friendsRepository.delete(friendsRepository.findBySenderAndReciever(userRepository.findByName(name), currentUser));
    }
    
    public List<User> friends(Model model, User currentUser) {
        
        //replace
        
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
            List<User> friends, List<Friends> friendRequests) {
        
        if (searchedUser == currentUser) {
            return false;
        }
        
        for (User user : friends) {
            if (searchedUser == user) {
                return false;
            }
        }
        
        for (Friends friend : friendRequests) {
            if (searchedUser == friend.getSender() &&
                    currentUser == friend.getReciever()) {
                return false;
            }
            
            if (currentUser == friend.getSender() &&
                    searchedUser == friend.getReciever()) {
                return false;
            }
        }
        
        return true;
    }
}
