/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;

import java.util.Properties;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author noob9
 */
@SpringBootApplication
public class ProjectApplication {
    
    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("EET"));
    }


    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }
}
