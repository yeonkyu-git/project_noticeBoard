package project.noticeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.noticeboard.entity.Member;
import project.noticeboard.entity.Post;
import project.noticeboard.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByPost(Post post);
}
