package project.noticeboard.dto;

import lombok.Data;

import java.sql.Blob;
import java.time.LocalDateTime;

@Data
public class PostDto {

    private Long postId;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostDto(Long postId, String title, String content, String username, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
