package project.noticeboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.noticeboard.entity.Member;
import project.noticeboard.repository.MemberRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public Long join(String email, String password, String username, int age) {
        validationEmail(email);
        Member member = new Member(email, password, username, age);
        memberRepository.save(member);
        return member.getId();
    }

    private void validationEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일 입니다.");
        }
    }

    /**
     * 로그인
     */
    public Long login(String email, String password) {
        Optional<Member> member = memberRepository.findByEmail(email);
        // Email Check
        if (member.isEmpty()) {
            throw new RuntimeException("해당하는 이메일이 없습니다.");
        }

        // Password Check
        if (!member.get().getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 맞지 않습니다.");
        }

        // get말고 orElseGet 으로 에러형 객체를 만들어 반환하는 것도 괜찮치 않을까?
        return member.get().getId();
    }



}
