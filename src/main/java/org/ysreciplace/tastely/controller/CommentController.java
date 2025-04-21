package org.ysreciplace.tastely.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.ysreciplace.tastely.entity.Comment;
import org.ysreciplace.tastely.entity.Recipe;
import org.ysreciplace.tastely.entity.User;
import org.ysreciplace.tastely.repository.CommentRepository;
import org.ysreciplace.tastely.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/comments")
@AllArgsConstructor
@Slf4j
public class CommentController {
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    @PostMapping("/add")
    @ResponseBody
    public Map<String,Object> addCommentHandle(@RequestParam Long recipeId,
                                               @RequestParam String content,
                                               @RequestParam Long userId) {


        Comment comment = new Comment();
        comment.setRecipeId(recipeId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.commentSave(comment);

        User user = userRepository.findById(userId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = String.format(comment.getCreatedAt(), formatter);
        System.out.println(formattedDate);


        Map<String, Object> result = new HashMap<>();
        result.put("id", comment.getId());
        result.put("content", comment.getContent());
        result.put("nickname", user.getNickname());
        result.put("createdAt", formattedDate);

        return result;
    }

    @GetMapping("/recipe/{recipeId}")
    public String findCommentByRecipeIdHandle(@PathVariable Long recipeId){
        commentRepository.findCommentByRecipeId(recipeId);
        return "redirect:/recipe/detail/" + recipeId;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteCommentHandle(@PathVariable Long id){
        commentRepository.commentDelete(id);
        return null;
    }

    @PostMapping("/update")
    public String commentUpdate(Comment comment) {
        commentRepository.commentUpdate(comment);
        return "redirect:/recipe/detail/" + comment.getRecipeId();
    }
}
