package org.ysreciplace.tastely.request;


import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String username;
    private String password;
    @Email
    private String email;
}
