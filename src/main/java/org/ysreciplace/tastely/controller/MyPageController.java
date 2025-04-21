package org.ysreciplace.tastely.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.ysreciplace.tastely.entity.Recipe;
import org.ysreciplace.tastely.entity.User;
import org.ysreciplace.tastely.repository.RecipeRepository;
import org.ysreciplace.tastely.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class MyPageController {

    private final RecipeRepository recipeRepository;
    private UserRepository userRepository;

    @GetMapping("/page")
    public String page(@SessionAttribute("user") Optional<User> user,
                       @RequestParam(defaultValue = "recipe") String tab,
                       Model model) {
        if (user.isEmpty()) {
            return "redirect:/auth/login";
        }

        User found = user.get();
        model.addAttribute("user", found);
        model.addAttribute("tab", tab);

        if ("favorite".equals(tab)) {
            // 즐겨찾기 불러오는 코드 👉 여기는 너가 이미 만든 FavoriteRepository로 바꿔줘야 함
            List<Recipe> favoriteRecipes = recipeRepository.findFavoriteByUserId((long) found.getId()); // 없으면 만들어야 함
            model.addAttribute("myRecipes", favoriteRecipes);
        } else {
            List<Recipe> myRecipes = recipeRepository.findByUserId((long) found.getId());
            model.addAttribute("myRecipes", myRecipes);
        }

        return "my/page";
    }
}
