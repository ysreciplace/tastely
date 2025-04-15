package org.ysreciplace.tastely.controller;  // 패키지 경로 수정

import org.ysreciplace.tastely.model.Recipe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class RankingController {

    // 홈 화면에서 음식 목록을 보여주는 메서드
    @GetMapping("/ranking")
    public String home(Model model) {
        // 음식 목록 데이터 (임시 데이터)
        List<Recipe> recipes = Arrays.asList(
                new Recipe("스키야키", "맛있는 스키야키 레시피", "/images/sukiyaki.jpg"),
                new Recipe("감바스", "무드있는 감바스 레시피", "/images/gambas.jpg"),
                new Recipe("타코", "멕시코 현지에 온 듯한 타코 레시피", "/images/taco.jpg"),
                new Recipe("감바스", "무드있는 감바스 레시피", "/images/gambas.jpg"),
                new Recipe("감바스", "무드있는 감바스 레시피", "/images/gambas.jpg")
        );

        // 모델에 레시피 데이터 추가
        model.addAttribute("popularRecipes", recipes);

        return "ranking";  // ranking.html 템플릿으로 포워딩
    }

}