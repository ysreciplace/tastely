package org.ysreciplace.tastely.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.ysreciplace.tastely.entity.Review;
import org.ysreciplace.tastely.repository.ReviewRepository;

@Controller
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {
    private ReviewRepository reviewRepository;

    @PostMapping("/add")
    public String addReviewHandle(@ModelAttribute Review review) {
        reviewRepository.save(review);
        return "redirect:/recipe/detail/" + review.getRecipeId();
    }

    @PostMapping("/update")
    public String updateReviewHandle(@ModelAttribute Review review) {
        reviewRepository.update(review);
        return "redirect:/recipe/detail/" + review.getRecipeId();
    }
    @PostMapping("/delete")
    public String deleteReviewHandle(@RequestParam("id") Long id,
                                     @RequestParam("recipeId") Long recipeId) {
        reviewRepository.delete(id);
        return "redirect:/recipe/detail/" + recipeId;
    }

    @GetMapping
    @ResponseBody
    public double getAverageRating(@PathVariable Long recipeId) {
        return reviewRepository.findAverageRatingByRecipeId(recipeId);
    }

}
