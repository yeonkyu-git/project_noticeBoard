package project.noticeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.noticeboard.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
