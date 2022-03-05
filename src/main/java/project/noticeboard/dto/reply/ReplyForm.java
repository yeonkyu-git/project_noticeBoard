package project.noticeboard.dto.reply;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReplyForm {

    private Long postId;
    private String content;

}
