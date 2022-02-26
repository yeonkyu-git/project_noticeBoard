package project.noticeboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.noticeboard.entity.Member;
import project.noticeboard.entity.Post;
import project.noticeboard.repository.postcustom.PostRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    // 전체 게시글을 조회하고 Paging 요건
    // fetch join으로 N+1 문제 해결
    // Post <-> Member 는 1:다 관계로 post 기준으로 fetch join 해도 paging 시 개수 문제는 발생하지 않음
    @Query(
            value = "select p from Post p "
            + "inner join fetch p.member",
            countQuery = "select count(p) from Post p"
    )
    Page<Post> findAll(Pageable pageable);

    Optional<Post> findByIdAndMember(Long id, Member member);
}
