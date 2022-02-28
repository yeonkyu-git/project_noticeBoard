package project.noticeboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.noticeboard.config.SHA256;
import project.noticeboard.entity.Member;
import project.noticeboard.repository.MemberRepository;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final SHA256 sha256;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(String email, String password, String username, int age) {
        try {
            validationEmail(email);
            String encryptPassword = sha256.encrypt(password);
            Member member = new Member(email, encryptPassword, username, age);
            memberRepository.save(member);
            return member.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new RuntimeException("에러 발생!");
    }

    private void validationEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일 입니다.");
        }
    }

    /**
     * 로그인
     */
    @Transactional
    public Long login(String email, String password) throws NoSuchAlgorithmException {

        try {
            Optional<Member> member = memberRepository.findByEmail(email);
            // Email Check
            if (member.isEmpty()) {
                throw new RuntimeException("해당하는 이메일이 없습니다.");
            }

            // Password Check
            String cryptogram = sha256.encrypt(password);
            if (!cryptogram.equals(member.get().getPassword())) {
                throw new RuntimeException("비밀번호가 맞지 않습니다.");
            }

            member.get().updateLastLoginDate();
            log.info("last Login = {} ", member.get().getLastLoginAt());

            // get말고 orElseGet 으로 에러형 객체를 만들어 반환하는 것도 괜찮치 않을까?
            return member.get().getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new RuntimeException("에러 발생!");
    }



}
