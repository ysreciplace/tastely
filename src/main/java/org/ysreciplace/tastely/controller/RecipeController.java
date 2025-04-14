package org.ysreciplace.tastely.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.ysreciplace.tastely.entity.Ingredient;
import org.ysreciplace.tastely.entity.Recipe;
import org.ysreciplace.tastely.entity.Step;
import org.ysreciplace.tastely.repository.IngredientRepository;
import org.ysreciplace.tastely.repository.RecipeRepository;
import org.ysreciplace.tastely.repository.StepRepository;
import org.ysreciplace.tastely.request.NewRecipe;

import java.util.List;

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
    public String historyPostHandel(@ModelAttribute NewRecipe newRecipe,
                                    Model model) {

        System.out.println("userId = " + newRecipe.getRecipe().getUserId());
        System.out.println("=========? "+  newRecipe.getRecipe().getId());
        recipeRepository.save( newRecipe.getRecipe());
        System.out.println("=======================? "+  newRecipe.getRecipe().getId());

        for (Ingredient one: newRecipe.getIngredients() ) {
            one.setRecipeId(newRecipe.getRecipe().getId());
            recipeRepository.ingredientSave(one);
        }

        for (Step one: newRecipe.getSteps() ) {
            one.setRecipeId(newRecipe.getRecipe().getId());
            recipeRepository.stepSave(one);
        }

        return "redirect:/recipe/history";

    }

}
