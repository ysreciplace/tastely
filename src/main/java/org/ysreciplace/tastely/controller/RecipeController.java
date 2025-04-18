package org.ysreciplace.tastely.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ysreciplace.tastely.entity.*;
import org.ysreciplace.tastely.repository.*;
import org.ysreciplace.tastely.request.NewRecipe;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/recipe")
@AllArgsConstructor
public class RecipeController {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final ReviewRepository reviewRepository;

    // ✅ 상세 페이지
    @GetMapping("/detail/{id}")
    public String getRecipeDetail(@PathVariable("id") Long id,
                                  @SessionAttribute("user") User user,
                                  Model model) {
        Recipe recipe = recipeRepository.findById(id);
        List<Review> reviews = reviewRepository.findByRecipeId(id);
        double averageRating = reviewRepository.findAverageRatingByRecipeId(id);

        model.addAttribute("recipe", recipe);
        model.addAttribute("reviews", reviews);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("user", user);
        model.addAttribute("currentUserId", 1L); // 임시 사용자 ID

        return "recipe/detail"; // templates/recipe/detail.html
    }

    // ✅ 레시피 등록 처리
    @PostMapping("/history")
    public String historyPostHandel(@ModelAttribute NewRecipe newRecipe,
                                    @RequestParam("thumbnailFile") MultipartFile thumbnailFile,
                                    Model model) throws IOException, InterruptedException {

        Recipe recipe = newRecipe.getRecipe();

        if (!thumbnailFile.isEmpty()) {
            String originalFilename = thumbnailFile.getOriginalFilename();
            String savedFilename = UUID.randomUUID() + "_" + originalFilename;

            File tempFile = File.createTempFile("upload-", "-" + savedFilename);
            thumbnailFile.transferTo(tempFile);

            String privateKeyPath = "C:\\awskey\\dev-kms-key.pem";
            String user = "ec2-user";
            String host = "54.180.114.141";
            String remoteDir = "/home/ec2-user/uploads";

            String[] scpCommand = {
                    "scp",
                    "-i", privateKeyPath,
                    "-o", "StrictHostKeyChecking=no",
                    tempFile.getAbsolutePath(),
                    user + "@" + host + ":" + remoteDir + "/" + savedFilename
            };

            ProcessBuilder pb = new ProcessBuilder(scpCommand);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("SCP failed with exit code " + exitCode);
            }

            recipe.setThumbnail("http://54.180.114.141/uploads/" + savedFilename);
            tempFile.delete();
        }

        recipeRepository.save(recipe);

        for (Ingredient one : newRecipe.getIngredients()) {
            one.setRecipeId(recipe.getId());
            recipeRepository.ingredientSave(one);
        }

        for (Step one : newRecipe.getSteps()) {
            one.setRecipeId(recipe.getId());
            recipeRepository.stepSave(one);
        }

        return "redirect:/recipe/history";
    }

    // ✅ 전체 레시피 페이지 (recipe.html)
    @GetMapping("/page")
    public String showRecipesPage(@RequestParam("keyword") Optional<String> keyword, Model model) {
//        List<Recipe> allRecipes = recipeRepository.findAll();
//        model.addAttribute("recipes", allRecipes);

        List<Recipe> searchResults = recipeRepository.findByTitleContain(keyword.orElse(""));
        model.addAttribute("recipes", searchResults);
        model.addAttribute("keyword", keyword.orElse(""));  // 검색어도 같이 넘겨줌 (뷰에서 보여줄 수 있게);
        return "recipe"; // templates/recipe.html
    }
}
