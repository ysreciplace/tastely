package org.ysreciplace.tastely.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailCheck {
    @Email
    private String email;
}
