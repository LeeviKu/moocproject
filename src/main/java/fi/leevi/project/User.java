/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author noob9
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractPersistable<Long> {

    private String username;
    private String password;
    private String name;
    private String path;
    @ManyToMany
    private List<Friends> friends = new ArrayList<>();
    
    public User(String username, String password, String name, String path) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.path = path;
    }

}
