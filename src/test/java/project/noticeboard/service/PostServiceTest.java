package project.noticeboard.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.noticeboard.dto.PostDto;
import project.noticeboard.entity.Member;
import project.noticeboard.entity.Post;
import project.noticeboard.repository.MemberRepository;
import project.noticeboard.repository.PostRepository;
import project.noticeboard.repository.postcustom.PostSearch;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    @BeforeEach
    public void init() {
        Long memberId = memberService.join("dusrbpoiiij@naver.com", "123456", "kim", 33);
        postService.createPost("죽", "이건 내용 영역입니다.", memberId);

        for (int i=0; i<100; i++) {
            Long memberId2 = memberService.join("dusrbpoiiij@naver.com" + i, "123456", "kim", 33);
            postService.createPost("안녕하세요" + i, "이건 내용 입니다." + i, memberId2);
        }
    }

    @Test
    public void 게시글등록() throws Exception {
        Member member = memberRepository.findByEmail("dusrbpoiiij@naver.com").get();
        em.flush();
        em.clear();
        Long postId = postService.createPost("이건 제목입니다.", "이건 내용 영역입니다.", member.getId());
        Post findPost = postRepository.findById(postId).get();

        assertThat(findPost.getTitle()).isEqualTo("이건 제목입니다.");
        assertThat(findPost.getContent()).isEqualTo("이건 내용 영역입니다.");
    }

    @Test
    public void 게시글수정() throws Exception {
        Member member = memberRepository.findByEmail("dusrbpoiiij@naver.com").get();
        Long postId = member.getPosts().get(0).getId();
        em.flush();
        em.clear();

        System.out.println(" ============================= ");
        postService.updatePost(postId, member.getId(), "이건 수정 제목입니다.", "이건 수정 내용입니다.");
        em.flush();
        em.clear();

        Post findPost = postRepository.findById(postId).get();
        assertThat(findPost.getTitle()).isEqualTo("이건 수정 제목입니다.");
        assertThat(findPost.getContent()).isEqualTo("이건 수정 내용입니다.");
    }

    @Test
    public void 게시글삭제() throws Exception {
        Member member = memberRepository.findByEmail("dusrbpoiiij@naver.com").get();
        Long postId = member.getPosts().get(0).getId();
        em.flush();
        em.clear();

        postService.deletePost(postId, member.getId());
        List<Post> result = postRepository.findAll();
        Assertions.assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void 게시글조회_전체() throws Exception {
        em.flush();
        em.clear();
        List<PostDto> result = postService.findAllPost(0, 20);
        assertThat(result.size()).isEqualTo(20);

        for (PostDto postDto : result) {
            System.out.println("postDto = " + postDto.getTitle());
        }
    }

    @Test
    public void 게시글조회_조건검색() throws Exception {
        em.flush();
        em.clear();
        PostSearch postSearch = new PostSearch("", "", "kim", 0, 20);
        List<PostDto> result = postService.findBySearch(postSearch);
        for (PostDto postDto : result) {
            System.out.println("postDto = " + postDto.getTitle());
        }
    }

}