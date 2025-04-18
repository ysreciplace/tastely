package org.ysreciplace.tastely.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String Page(@SessionAttribute("user") Optional<User> user, Model model) {
        if (user.isEmpty()) {
            return "redirect:/auth/login";
        }

        User found = user.get();
        List<Recipe> myRecipes = recipeRepository.findByUserId((long) found.getId());

        model.addAttribute("user", found);
        model.addAttribute("myRecipes", myRecipes);

        return "my/page";
    }
}
