package project.noticeboard.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.noticeboard.config.SHA256;
import project.noticeboard.entity.Member;
import project.noticeboard.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    SHA256 sha256 = new SHA256();


    @BeforeEach
    private void init() throws NoSuchAlgorithmException {
        Member member = new Member("dusrbpoiiij@naver.com", sha256.encrypt("123456"), "kim", 33);
        em.persist(member);
    }


    @Test
    public void 회원가입() throws Exception {
        Long memberId = memberService.join("test@naver.com", "1234567", "kim", 33);
        Member member = memberRepository.findById(memberId).get();

        assertThat(member).isEqualTo(member);
        assertThat(member.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    public void 로그인성공() throws Exception {
        Long memberId = memberService.login("dusrbpoiiij@naver.com", "123456");
        Member member = memberRepository.findById(memberId).get();

        assertThat(member).isEqualTo(member);
        assertThat(member.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    public void 로그인실패_이메일없음() throws Exception {
        assertThrows(RuntimeException.class, () -> memberService.login("test@naver.com", "123456"));
    }

    @Test
    public void 로그인실패_비밀번호_틀림() throws Exception {
        assertThrows(RuntimeException.class, () -> memberService.login("dusrbpoiiij@naver.com", "1234566"));
    }
}