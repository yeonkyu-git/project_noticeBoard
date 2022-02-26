package project.noticeboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.noticeboard.entity.Member;
import project.noticeboard.entity.Post;
import project.noticeboard.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // 전체 게시글을 조회하고 Paging 요건
    // fetch join으로 N+1 문제 해결
    // Post <-> Member 는 1:다 관계로 post 기준으로 fetch join 해도 paging 시 개수 문제는 발생하지 않음
    @Query(
            value = "select r from Reply r "
                    + "inner join fetch r.member "
                    + "where r.post = :post",
            countQuery = "select count(r) from Reply r"
    )
    Page<Reply> findReplyWithPostAndMember(@Param("post") Post post, Pageable pageable);



    Page<Reply> findByPost(Post post, Pageable pageable);
}
