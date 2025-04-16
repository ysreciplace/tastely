package org.ysreciplace.tastely.entity;

import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private Long id;
    private Long userId;
    private Long recipeId;
    private LocalDateTime createdAt;
}
