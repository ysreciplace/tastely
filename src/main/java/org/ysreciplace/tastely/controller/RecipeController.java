package org.ysreciplace.tastely.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ysreciplace.tastely.entity.Recipe;
import org.ysreciplace.tastely.repository.RecipeRepository;

@Controller
@RequestMapping("/recipe")
@AllArgsConstructor
public class RecipeController {

    private RecipeRepository recipeRepository;

    @GetMapping("/history")
    public String historyHandel(Model model) {

        return "recipe/history";
    }

    @PostMapping("/history")
    public String historyPostHandel(@ModelAttribute Recipe recipe, Model model) {
        recipeRepository.save(recipe);
        return "redirect:/recipe/history";

    }

}
