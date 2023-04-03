package dev.swcats.keycloakoauth2demo.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserModel {
    Long id;
    String username;
    String email;
}
