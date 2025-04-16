package org.ysreciplace.tastely.repository;

import org.apache.ibatis.annotations.Mapper;
import org.ysreciplace.tastely.entity.Ingredient;

@Mapper
public interface IngredientRepository {
    public int ingredientSave(Ingredient ingredient);
}
