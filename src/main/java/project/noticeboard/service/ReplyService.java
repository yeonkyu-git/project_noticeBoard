package project.noticeboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.noticeboard.dto.ReplyDto;
import project.noticeboard.entity.Member;
import project.noticeboard.entity.Post;
import project.noticeboard.entity.Reply;
import project.noticeboard.repository.MemberRepository;
import project.noticeboard.repository.PostRepository;
import project.noticeboard.repository.ReplyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReplyService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    /**
     * 댓글 등록
     */
    public void createReply(String content, Long memberId, Long postId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Post> post = postRepository.findById(postId);

        if (member.isPresent() && post.isPresent()) {
            replyRepository.save(Reply.createReply(content, member.get(), post.get()));
        } else {
            throw new RuntimeException("댓글 등록에 실패하였습니다");
        }
    }

    /**
     * 댓글 수정
     */
    public void updateReply(String content, Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(RuntimeException::new);
        reply.updateReply(content);
    }

    /**
     * 댓글 삭제
     */
    public void deleteReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(RuntimeException::new);
        replyRepository.delete(reply);
    }

    /**
     * 댓글 조회
     */
    public List<ReplyDto> findByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        List<Reply> replies = replyRepository.findByPost(post);
        return replies.stream()
                .map(r -> new ReplyDto(
                        r.getId(),
                        r.getContent(),
                        r.getMember().getUsername(),
                        r.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }


}
