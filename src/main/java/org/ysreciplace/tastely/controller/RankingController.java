package org.ysreciplace.tastely.controller;  // 패키지 경로 수정

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.ysreciplace.tastely.entity.Recipe;
import org.ysreciplace.tastely.repository.RankingRepository;
import org.ysreciplace.tastely.repository.RecipeRepository;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class RankingController {
    private RankingRepository rankingRepository;
    // 홈 화면에서 음식 목록을 보여주는 메서드
    @GetMapping("/ranking")
    public String home(Model model) {
        // 음식 목록 데이터 (임시 데이터)
        List<Recipe> popularRecipes = Arrays.asList(
                Recipe.builder().title("스키야키").description( "맛있는 스키야키 레시피").thumbnail("/images/sukiyaki.jpg").build(),
                Recipe.builder().title("감바스").description( "무드있는 감바스 레시피").thumbnail("/images/gambas.jpg").build(),
                Recipe.builder().title("타코").description( "멕시코 현지에 온 듯한 타코 레시피").thumbnail("/images/taco.jpg").build(),
                Recipe.builder().title("피자").description( "치즈가 쭉쭉 늘어나는 피자 레시피").thumbnail("/images/pizza.jpg").build(),
                Recipe.builder().title("팟타이").description( "태국의 정통 팟타이 레시피").thumbnail("/images/padthai.jpg").build()
        );

        // 모델에 레시피 데이터 추가
        model.addAttribute("popularRecipes", popularRecipes);

        List<Recipe> newRecipes =rankingRepository.findLatestRecipes();
        model.addAttribute("newRecipes", newRecipes);

        List<Recipe> quickRecipes =rankingRepository.findQuickRecipes();
        model.addAttribute("quickRecipes", quickRecipes);
        return "ranking";  // ranking.html 템플릿으로 포워딩
    }

    @GetMapping("/recipe/view")
    public String recipeDetailHandle(@RequestParam("id") Long id, Model model) {
        model.addAttribute("recipe" , rankingRepository.getRecipeDetailById(id));
        model.addAttribute("ingredients" , rankingRepository.getIngredientsByRecipeId(id));
        model.addAttribute("steps" , rankingRepository.getStepsByRecipeId(id));

        return "recipe/view";
    }

}