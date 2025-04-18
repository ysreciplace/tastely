package org.ysreciplace.tastely.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private String nickname;
    private LocalDateTime createAt;// 유저 닉네임
}
