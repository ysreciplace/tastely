package org.ysreciplace.tastely.entity;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Verification {
    private int id;
    private String token;
    private String userEmail;
    private LocalDateTime expiresAt;
}
