/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lepe
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserIn(List<User> userList, Pageable pageable);
}
