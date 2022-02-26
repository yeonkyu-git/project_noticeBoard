package project.noticeboard.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;
    private String content;
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    //== 연관관계 메소드 ==//
    public void setPost(Post post) {
        this.post = post;
        post.getReplies().add(this);
    }
}
