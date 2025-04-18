package org.ysreciplace.tastely.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.ysreciplace.tastely.entity.Favorite;
import org.ysreciplace.tastely.entity.User;
import org.ysreciplace.tastely.repository.FavoriteRepository;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/favorite")
@AllArgsConstructor
public class FavoriteController {
    private final ObjectMapper objectMapper;
    private FavoriteRepository favoriteRepository;

    @PostMapping("/toggle")
    @ResponseBody
    public String toggleFavorite(@RequestParam Long recipeId,
                                 @SessionAttribute("user") User user) throws JsonProcessingException {
        boolean exists = favoriteRepository.exists((long)user.getId(),recipeId);
        if(exists) {
            favoriteRepository.delete((long)user.getId(),recipeId);
        } else {
            Favorite favorite = new Favorite();
            favorite.setUserId((long)user.getId());
            favorite.setRecipeId(recipeId);
            favoriteRepository.save(favorite);
        }

        boolean isFavorite = favoriteRepository.exists((long)user.getId(), recipeId);

        Map<String, Object> response = new HashMap<>();
        response.put("isFavorite", !isFavorite);
        return objectMapper.writeValueAsString(response);
    }
}
