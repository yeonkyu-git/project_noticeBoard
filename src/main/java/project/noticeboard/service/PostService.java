package project.noticeboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.noticeboard.dto.PostDto;
import project.noticeboard.entity.Member;
import project.noticeboard.entity.Post;
import project.noticeboard.repository.MemberRepository;
import project.noticeboard.repository.PostRepository;
import project.noticeboard.repository.postcustom.PostSearch;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /**
     * 게시글 등록
     */
    public Long createPost(String title, String content, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
        Post post = Post.createPost(title, content, member);
        postRepository.save(post);
        return post.getId();
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void updatePost(Long postId, Long memberId, String title, String content) {
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
        Post post = postRepository.findByIdAndMember(postId, member).orElseThrow(RuntimeException::new);
        post.updatePost(title, content);
    }

    /**
     * 게시글 삭제
     */
    public void deletePost(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
        Post post = postRepository.findByIdAndMember(postId, member).orElseThrow(RuntimeException::new);

        // post에 등록되어 있는 모든 댓글들도 같이 삭제되게끔 한다.
        // postId에 해당하는 댓글 가져오기
        // 댓글 삭제 처리  (batch size 1000 으로 해서 성능 최적화)
        // post 삭제 처리

        postRepository.delete(post);
    }

    /**
     * 게시글 조회  ->  Spring Data JPA Paging
     */
    public Page<PostDto> findAllPost(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAllPosts(pageRequest);
    }

    /**
     * 게시글 검색 ( 조건 : 제목, 내용, 작성자 )  -> Querydsl
     */
    public Page<PostDto> findBySearch(PostSearch search) {
        PageRequest pageRequest = PageRequest.of(search.getPage(), search.getSize());
        return postRepository.findPostSearch(search, pageRequest);
    }

}
