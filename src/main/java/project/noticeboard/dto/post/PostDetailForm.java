package project.noticeboard.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDetailForm {
    private Long postId;
    private String title;
    private String content;
    private Long memberId;
}
