package org.ysreciplace.tastely.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile imageFile;
}
