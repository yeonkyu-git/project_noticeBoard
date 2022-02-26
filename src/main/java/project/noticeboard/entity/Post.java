package project.noticeboard.entity;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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


    //== 연관관계 메소드 ==//
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }
}
