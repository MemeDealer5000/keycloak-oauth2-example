package dev.swcats.keycloakoauth2demo.service;

import dev.swcats.keycloakoauth2demo.model.UserModel;
import dev.swcats.keycloakoauth2demo.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserModel getUserByName(String username){
        val user = userRepository.getUser(username);
        if(user.isEmpty()){
            throw new RuntimeException("This user does not exist");
        }
        return user.get();
    }

    public Boolean doesUserExists(String username){
        val user = userRepository.getUser(username);
        return user.isPresent();
    }
}
