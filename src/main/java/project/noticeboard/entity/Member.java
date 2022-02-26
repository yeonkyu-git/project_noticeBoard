package project.noticeboard.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

    @OneToMany(mappedBy = "member")
    List<Post> posts = new ArrayList<>();
}
