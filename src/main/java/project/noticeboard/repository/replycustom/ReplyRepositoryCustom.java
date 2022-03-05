package project.noticeboard.repository.replycustom;

import project.noticeboard.dto.ReplyDto;
import project.noticeboard.entity.Post;

import java.util.List;

public interface ReplyRepositoryCustom {

    List<ReplyDto> findAllReplyByPost(Post post);
}
