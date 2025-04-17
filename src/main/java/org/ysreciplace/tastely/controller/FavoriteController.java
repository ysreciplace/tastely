package org.ysreciplace.tastely.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.ysreciplace.tastely.entity.Favorite;
import org.ysreciplace.tastely.entity.User;
import org.ysreciplace.tastely.repository.FavoriteRepository;

@Controller
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {
    private FavoriteRepository favoriteRepository;

    @PostMapping("/toggle")
    public String toggleFavorite(@RequestParam Long recipeId,
                                 @SessionAttribute("user") User user) {
        boolean exists = favoriteRepository.exists((long)user.getId(),recipeId);
        if(exists) {
            favoriteRepository.delete((long)user.getId(),recipeId);
        } else {
            Favorite favorite = new Favorite();
            favorite.setUserId((long)user.getId());
            favorite.setRecipeId(recipeId);
            favoriteRepository.save(favorite);
        }
        return "redirect:/recipe/detail/" + recipeId;
    }
}
