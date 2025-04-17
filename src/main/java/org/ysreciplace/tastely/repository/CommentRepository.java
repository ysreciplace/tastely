package org.ysreciplace.tastely.repository;

import org.apache.ibatis.annotations.Mapper;
import org.ysreciplace.tastely.entity.Comment;


import java.util.List;

@Mapper
public interface CommentRepository {
    public int commentSave(Comment comment);
    public List<Comment> findCommentByRecipeId(Long recipeId);
    public int commentUpdate(Comment comment);
    public Long commentDelete(Long id);
}
