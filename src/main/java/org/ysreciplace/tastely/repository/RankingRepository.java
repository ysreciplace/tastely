package org.ysreciplace.tastely.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ysreciplace.tastely.entity.Ingredient;
import org.ysreciplace.tastely.entity.Recipe;
import org.ysreciplace.tastely.entity.Step;

import java.util.List;

@Mapper
public interface RankingRepository {
    List<Recipe> findLatestRecipes();
    List<Recipe> findQuickRecipes();
    List<Recipe> findPopularRecipes();

    public Recipe getRecipeDetailById(@Param("id") Long Id);
    public List<Ingredient> getIngredientsByRecipeId(@Param("recipeId") Long recipeId);
    public List<Step> getStepsByRecipeId(@Param("recipeId") Long recipeId);
}
