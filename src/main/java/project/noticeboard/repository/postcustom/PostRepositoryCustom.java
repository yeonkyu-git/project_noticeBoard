package project.noticeboard.repository.postcustom;

import project.noticeboard.dto.PostDto;
import project.noticeboard.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findPostSearch(PostSearch search);
}
