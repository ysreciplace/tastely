package org.ysreciplace.tastely.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String thumbnail;
    private Integer servings;
    private Integer cookTime; // 분 단위
    private String difficulty;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String videoUrl;
}
