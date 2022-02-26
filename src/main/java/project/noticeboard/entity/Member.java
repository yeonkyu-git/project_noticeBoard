package project.noticeboard.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String username;
    private int age;
    private LocalDateTime lastLoginAt;

    public Member(String email, String password, String username, int age) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.age = age;
    }

    @OneToMany(mappedBy = "member")
    List<Post> posts = new ArrayList<>();
}
