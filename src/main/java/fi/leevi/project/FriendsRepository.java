/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author noob9
 */
public interface FriendsRepository extends JpaRepository<Friends, Long> {
    List<Friends> findByReciever(User reciever);
    
}
