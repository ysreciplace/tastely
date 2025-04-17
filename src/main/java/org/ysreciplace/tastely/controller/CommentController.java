package org.ysreciplace.tastely.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.ysreciplace.tastely.entity.Comment;
import org.ysreciplace.tastely.repository.CommentRepository;

@Controller
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private CommentRepository commentRepository;

    @PostMapping("/add")
    public String addCommentHandle(@ModelAttribute Comment comment) {
        commentRepository.commentSave(comment);
        return "redirect:/recipe/detail/" + comment.getRecipeId();
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
