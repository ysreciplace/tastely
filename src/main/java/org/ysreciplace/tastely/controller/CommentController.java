package org.ysreciplace.tastely.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.ysreciplace.tastely.entity.Comment;
import org.ysreciplace.tastely.entity.User;
import org.ysreciplace.tastely.repository.CommentRepository;
import org.ysreciplace.tastely.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    @PostMapping("/add")
    @ResponseBody
    public Map<String,Object> addCommentHandle(@RequestParam Long recipeId,
                                               @RequestParam Long userId,
                                               @RequestParam String content) {
        Comment comment = new Comment();
        comment.setRecipeId(recipeId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.commentSave(comment);

        User user = userRepository.findById(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("id", comment.getId());
        result.put("content", comment.getContent());
        result.put("nickname", user.getNickname());

        return result;
    }

    @GetMapping("recipe/{recipeId}")
    public String findCommentByRecipeIdHandle(@PathVariable Long recipeId){
        commentRepository.findCommentByRecipeId(recipeId);
        return "redirect:/recipe/detail/" + recipeId;
    }

    @PostMapping("/{id}")
    public String deleteCommentHandle(@PathVariable Long id){
        commentRepository.commentDelete(id);
        return "redirect:/recipe/detail/" + id;
    }

    @PostMapping("/update")
    public String commentUpdate(Comment comment) {
        commentRepository.commentUpdate(comment);
        return "redirect:/recipe/detail/" + comment.getRecipeId();
    }
}
