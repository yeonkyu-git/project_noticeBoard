package project.noticeboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.noticeboard.entity.Post;
import project.noticeboard.repository.postcustom.PostRepositoryCustom;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Page<Post> findAll(Pageable pageable);
}
