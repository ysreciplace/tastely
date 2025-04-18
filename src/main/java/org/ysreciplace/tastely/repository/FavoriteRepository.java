package org.ysreciplace.tastely.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ysreciplace.tastely.entity.Favorite;
import org.ysreciplace.tastely.entity.Recipe;

import java.util.List;

@Mapper
public interface FavoriteRepository {
    int save(Favorite favorite);
    int delete(@Param("userId")Long userId, @Param("recipeId")Long recipeId);
    boolean exists(@Param("userId")Long userId, @Param("recipeId")Long recipeId);
    List<Recipe> findFavoritesByUserId(Long userId);
}
