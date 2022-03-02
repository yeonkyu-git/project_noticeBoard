package project.noticeboard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.noticeboard.config.SHA256;
import project.noticeboard.entity.Member;
import project.noticeboard.repository.MemberRepository;
import project.noticeboard.service.PostService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestDataInit {

    private final InitService initService;

    @PostConstruct
    public void init() throws NoSuchAlgorithmException {
        initService.dbInit1();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    @Slf4j
    static class InitService {
        private final MemberRepository memberRepository;
        private final PostService postService;
        private final SHA256 sha256;
        private final EntityManager em;


        public void dbInit1() throws NoSuchAlgorithmException {
            String password = sha256.encrypt("123");
            Member member = memberRepository.save(new Member("admin@admin.com", password, "김연규", 30));


            log.info("member Collection : {}", member.getPosts());
            for (int i = 0; i < 50; i++) {

                if (i % 2 == 0) {
                    postService.createPost("title " + i, "content " + i, member.getId());
                } else {
                    postService.createPost("제목 " + i, "내용 " + i, member.getId());
                }

            }
        }


    }

}
