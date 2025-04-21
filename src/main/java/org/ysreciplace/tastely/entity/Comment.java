package org.ysreciplace.tastely.entity;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long recipeId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    public void setCreatedAt(LocalDateTime now) {
    }

    public String getCreatedAt() {
        return "";
    }
}
