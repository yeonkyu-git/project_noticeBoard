package project.noticeboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;
    private String content;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    //== 생성자 메소드 ==//
    public Reply(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    //== 연관관계 메소드 ==//
    public void setPost(Post post) {
        this.post = post;
        post.getReplies().add(this);
    }

    //== 비즈니스 로직 ==//
    public static Reply createReply(String content, Member member, Post post) {
        Reply reply = new Reply(content, member);
        reply.setPost(post);
        return reply;
    }
}
