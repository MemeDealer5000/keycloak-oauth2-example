package dev.swcats.keycloakoauth2demo.controller;

import dev.swcats.keycloakoauth2demo.model.UserModel;
import dev.swcats.keycloakoauth2demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/user/{username}")
    public UserModel getUser(@PathVariable String username){
        return userService.getUserByName(username);
    }

    @GetMapping("/user/check/{username}")
    public Boolean checkUserExists(@PathVariable String username){
        return userService.doesUserExists(username);
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return "You are admin";
    }

}
