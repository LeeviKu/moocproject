/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author noob9
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByPath(String path);
    User findByName(String name);
}

