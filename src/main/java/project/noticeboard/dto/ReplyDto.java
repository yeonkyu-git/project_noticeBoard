package project.noticeboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyDto {

    private Long replyId;
    private Long memberId;
    private String content;
    private String username;
    private LocalDateTime createdAt;

    @QueryProjection
    public ReplyDto(Long replyId, Long memberId,String content, String username, LocalDateTime createdAt) {
        this.replyId = replyId;
        this.memberId = memberId;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }
}
