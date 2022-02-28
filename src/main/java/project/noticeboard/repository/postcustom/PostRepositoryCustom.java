package project.noticeboard.repository.postcustom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.noticeboard.dto.PostDto;
import project.noticeboard.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    Page<PostDto> findPostSearch(PostSearch search, Pageable pageable);

    Page<PostDto> findAllPosts(Pageable pageable);
}
