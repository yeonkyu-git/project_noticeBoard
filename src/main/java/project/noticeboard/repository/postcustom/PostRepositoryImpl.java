package project.noticeboard.repository.postcustom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import project.noticeboard.dto.PostDto;
import project.noticeboard.entity.Post;
import project.noticeboard.entity.QMember;
import project.noticeboard.entity.QPost;

import javax.persistence.EntityManager;
import java.sql.Blob;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static project.noticeboard.entity.QMember.member;
import static project.noticeboard.entity.QPost.post;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findPostSearch(PostSearch search) {
        return queryFactory
                .selectFrom(post)
                .leftJoin(member)
                .where(
                        titleContain(search.getTitle()),
                        contentContain(search.getContent()),
                        usernameContain(search.getUsername())
                )
                .orderBy(post.createdAt.desc())
                .offset(search.getOffset())
                .limit(search.getLimit())
                .fetch();
    }

    private BooleanExpression titleContain(String title) {
        return hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression contentContain(String content) {
        return hasText(content) ? post.content.contains(content) : null;
    }

    private BooleanExpression usernameContain(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }
}
