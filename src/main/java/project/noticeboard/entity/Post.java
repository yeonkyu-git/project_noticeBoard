package project.noticeboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private Blob content;
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    List<Reply> replies = new ArrayList<>();

    // 생성자 메소드
    public Post(String title, Blob content) {
        this.title = title;
        this.content = content;
    }

    //== 연관관계 메소드 ==//
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    //== 비즈니스 로직 ==//
    public static Post createPost(String title, Blob content, Member member) {
        Post post = new Post(title, content);
        post.setMember(member);
        return post;
    }
}
