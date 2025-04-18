package org.ysreciplace.tastely.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.ysreciplace.tastely.entity.Comment;
import org.ysreciplace.tastely.entity.CommentResponse;


import java.util.List;

@Mapper
public interface CommentRepository {
    public int commentSave(Comment comment);
    public List<Comment> findCommentByRecipeId(Long recipeId);
    public int commentUpdate(Comment comment);
    public Long commentDelete(Long id);
    List<CommentResponse> findAllByRecipeIdWithNickname(@Param("recipeId") Long recipeId);
}
