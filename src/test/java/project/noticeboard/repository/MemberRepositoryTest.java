package project.noticeboard.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.noticeboard.entity.Member;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        Member member = new Member("dusrbpoiij@naver.com", "1234", "kim", 30);
        Member findMember = memberRepository.save(member);
        Assertions.assertThat(findMember).isEqualTo(member);
        Assertions.assertThat(findMember.getUsername()).isEqualTo("kim");
    }

}