package project.noticeboard.dto.post;

import lombok.Data;

@Data
public class PostEditForm {
    private Long postId;
    private String title;
    private String content;
    private Long memberId;
}
