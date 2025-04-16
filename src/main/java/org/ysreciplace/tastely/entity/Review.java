package org.ysreciplace.tastely.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long id;
    private Long recipeId;
    private Long userId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
