package org.ysreciplace.tastely.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ysreciplace.tastely.entity.Review;

import java.util.List;

@Mapper
public interface ReviewRepository {
    public int save(Review review);
    public int update(Review review);
    public Long delete(Long id);
    public List<Review> findByRecipeId(Long recipeId);
    public double findAverageRatingByRecipeId(Long recipeId);
}
