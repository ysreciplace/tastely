package org.ysreciplace.tastely.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Step {
    private Long id;
    private Long recipeId;
    private int stepNumber;
    private String description;
    private String image;
}
