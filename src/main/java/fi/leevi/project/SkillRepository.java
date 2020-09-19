/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lepe
 */
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByUser(User user, Pageable pageable);
    List<Skill> findByUser(User user);
    Skill findBySkillAndUser(String skill, User user);
}
