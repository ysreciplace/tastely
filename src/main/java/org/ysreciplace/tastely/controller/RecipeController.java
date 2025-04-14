package org.ysreciplace.tastely.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ysreciplace.tastely.entity.Ingredient;
import org.ysreciplace.tastely.entity.Recipe;
import org.ysreciplace.tastely.entity.Step;
import org.ysreciplace.tastely.repository.IngredientRepository;
import org.ysreciplace.tastely.repository.RecipeRepository;
import org.ysreciplace.tastely.repository.StepRepository;
import org.ysreciplace.tastely.request.NewRecipe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
                                    @RequestParam("thumbnailFile") MultipartFile thumbnailFile,
                                    Model model) throws IOException {

        Recipe recipe = newRecipe.getRecipe();
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/thumbnails/";
        if (!thumbnailFile.isEmpty()) {
            String originalFilename = thumbnailFile.getOriginalFilename();
            String savedFilename = UUID.randomUUID() + "_" + originalFilename;
            Path savedPath = Paths.get(uploadDir,savedFilename);

            Files.createDirectories(savedPath.getParent());
            thumbnailFile.transferTo(savedPath.toFile());
            //썸네일 경로를 DB에 저장
            recipe.setThumbnail("/" + uploadDir + savedFilename);
        }

        System.out.println("userId = " + newRecipe.getRecipe().getUserId());
        System.out.println("=========? "+  newRecipe.getRecipe().getId());
        recipeRepository.save(recipe);
        //recipeRepository.save(newRecipe.getRecipe());
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
