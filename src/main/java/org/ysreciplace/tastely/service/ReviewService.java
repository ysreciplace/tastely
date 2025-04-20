package org.ysreciplace.tastely.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ysreciplace.tastely.entity.Review;
import org.ysreciplace.tastely.repository.ReviewRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviewsByRecipeId(Long recipeId) {
        return reviewRepository.findByRecipeId(recipeId);
    }
}
