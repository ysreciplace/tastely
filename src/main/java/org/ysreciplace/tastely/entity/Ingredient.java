package org.ysreciplace.tastely.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    private Long id;
    private Long recipeId;
    private String name;
    private String amount;
}
