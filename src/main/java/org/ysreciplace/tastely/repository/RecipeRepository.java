package org.ysreciplace.tastely.repository;

import org.apache.ibatis.annotations.Mapper;
import org.ysreciplace.tastely.entity.Recipe;

@Mapper
public interface RecipeRepository {
    public int save(Recipe recipe);


}
