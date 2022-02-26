package project.noticeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.noticeboard.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
