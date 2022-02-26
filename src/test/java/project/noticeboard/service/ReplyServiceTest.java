package project.noticeboard.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.noticeboard.dto.PostDto;
import project.noticeboard.dto.ReplyDto;
import project.noticeboard.entity.Member;
import project.noticeboard.entity.Post;
import project.noticeboard.entity.Reply;
import project.noticeboard.repository.MemberRepository;
import project.noticeboard.repository.ReplyRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ReplyServiceTest {

    @Autowired
    ReplyService replyService;
    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ReplyRepository replyRepository;
    @PersistenceContext
    EntityManager em;

    @BeforeEach
    public void init() {
        Long memberId = memberService.join("dusrbpoiiij@naver.com", "123456", "kim", 33);
        Long postId = postService.createPost("죽", "이건 내용 영역입니다.", memberId);
        Long postId2 = postService.createPost("죽", "이건 내용 영역입니다.", memberId);
        replyService.createReply("이건 댓글입니다.", memberId, postId);

        for (int i = 0; i < 100; i++) {
            Long memberId2 = memberService.join("dusrbpoiiij@naver.com" + i, "123456", "kim", 33);
            if (i % 2 == 0) {
                replyService.createReply("이건 댓글입니다."+i, memberId2, postId);
            } else {
                replyService.createReply("이건 댓글입니다."+i, memberId2, postId2);
            }

        }
    }



    @Test
    public void 댓글등록() throws Exception {
        Member member = memberRepository.findByEmail("dusrbpoiiij@naver.com").get();
        Long postId = member.getPosts().get(0).getId();
        em.flush();
        em.clear();

        Long replyId = replyService.createReply("이건 댓글입니다.", member.getId(), postId);
        Reply result = replyRepository.findById(replyId).get();

        assertThat(result.getContent()).isEqualTo("이건 댓글입니다.");
    }

    @Test
    public void 댓글수정() throws Exception {
        Member member = memberRepository.findByEmail("dusrbpoiiij@naver.com").get();
        Post post = member.getPosts().get(0);
        Long replyId = post.getReplies().get(0).getId();

        em.flush();
        em.clear();

        Reply reply = replyRepository.findById(replyId).get();
        reply.updateReply("수정");

        em.flush();
        em.clear();

        Reply findReply = replyRepository.findById(replyId).get();
        assertThat(findReply.getContent()).isEqualTo("수정");

    }

    @Test
    public void 댓글삭제() throws Exception {
        Member member = memberRepository.findByEmail("dusrbpoiiij@naver.com").get();
        Post post = member.getPosts().get(0);
        Long replyId = post.getReplies().get(0).getId();

        em.flush();
        em.clear();

        replyService.deleteReply(replyId);
        List<Reply> result = replyRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void 댓글조회() throws Exception {
        em.flush();
        em.clear();
        Member member = memberRepository.findByEmail("dusrbpoiiij@naver.com").get();
        Post post = member.getPosts().get(0);

        em.flush();
        em.clear();

        List<ReplyDto> result = replyService.findByPostId(post.getId(), 1, 20);
        for (ReplyDto replyDto : result) {
            System.out.println("replyDto = " + replyDto.getContent());
        }
    }

}