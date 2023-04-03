package dev.swcats.keycloakoauth2demo.repository;

import dev.swcats.keycloakoauth2demo.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    List<UserModel> users = List.of(
            UserModel.builder()
                    .id(1L)
                    .email("test@mail")
                    .username("sam")
                    .build(),
            UserModel.builder()
                    .id(2L)
                    .email("test@mail")
                    .username("john")
                    .build(),
            UserModel.builder()
                    .id(3L)
                    .email("test@mail")
                    .username("max")
                    .build());
    public Optional<UserModel> getUser(String username){
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}
