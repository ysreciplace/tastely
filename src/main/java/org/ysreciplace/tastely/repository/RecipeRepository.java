package org.ysreciplace.tastely.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ysreciplace.tastely.entity.Ingredient;
import org.ysreciplace.tastely.entity.Recipe;
import org.ysreciplace.tastely.entity.Step;

import java.util.List;

@Mapper
public interface RecipeRepository {
    public int save(Recipe recipe);
    public int ingredientSave(Ingredient ingredient);
    public int stepSave(Step step);
    public Recipe findById(@Param("id") Long id);
    public List<Recipe> searchByKeyword(@Param("keyword")String keyword);

}
