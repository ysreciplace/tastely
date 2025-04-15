package org.ysreciplace.tastely.repository;

import org.apache.ibatis.annotations.Mapper;
import org.ysreciplace.tastely.entity.Ingredient;
import org.ysreciplace.tastely.entity.Recipe;
import org.ysreciplace.tastely.entity.Step;

@Mapper
public interface RecipeRepository {
    public int save(Recipe recipe);
    public int ingredientSave(Ingredient ingredient);
    public int stepSave(Step step);

}
