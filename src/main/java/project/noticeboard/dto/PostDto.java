package project.noticeboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PostDto {

    private Long postId;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @QueryProjection
    public PostDto(Long postId, String title, String content, String username, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
