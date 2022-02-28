package project.noticeboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.noticeboard.config.SHA256;
import project.noticeboard.entity.Member;
import project.noticeboard.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final SHA256 sha256;

    @PostConstruct
    public void init() throws NoSuchAlgorithmException {
        String password = sha256.encrypt("123");
        memberRepository.save(new Member("admin@admin.com", password, "김연규", 30));
    }
}
