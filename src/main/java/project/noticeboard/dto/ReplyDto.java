package project.noticeboard.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyDto {

    private Long replyId;
    private String content;
    private String username;
    private LocalDateTime createdAt;

    public ReplyDto(Long replyId, String content, String username, LocalDateTime createdAt) {
        this.replyId = replyId;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }
}
