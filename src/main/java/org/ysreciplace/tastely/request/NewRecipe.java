package org.ysreciplace.tastely.request;

import lombok.Getter;
import lombok.Setter;
import org.ysreciplace.tastely.entity.Ingredient;
import org.ysreciplace.tastely.entity.Recipe;
import org.ysreciplace.tastely.entity.Step;

import java.util.List;

@Setter
@Getter
public class NewRecipe {
    Recipe recipe;
    List<Ingredient> ingredients;
    List<Step> steps;
}
