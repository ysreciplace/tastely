package org.ysreciplace.tastely.entity;


import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String profileImage;
    private String role;
    private String provider;
    private String providerId;
    private String verified;
    private LocalDateTime createdAt;
}
