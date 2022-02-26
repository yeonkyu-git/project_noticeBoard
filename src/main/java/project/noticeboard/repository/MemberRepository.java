package project.noticeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.noticeboard.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

//    Optional<Member> findByEmailAndPassword(String email, String password);
}
