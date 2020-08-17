/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.leevi.project;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author noob9s
 */

@Controller
public class DefaultController {
    
    @ResponseBody
    @GetMapping("/")
    public String home() {
        return "asd";
    }
}
