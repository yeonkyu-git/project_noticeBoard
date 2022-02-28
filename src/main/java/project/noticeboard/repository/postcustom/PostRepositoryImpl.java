package project.noticeboard.repository.postcustom;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import project.noticeboard.dto.PostDto;
import project.noticeboard.dto.QPostDto;
import project.noticeboard.entity.Post;
import project.noticeboard.entity.QMember;
import project.noticeboard.entity.QPost;

import javax.persistence.EntityManager;
import java.sql.Blob;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static project.noticeboard.entity.QMember.member;
import static project.noticeboard.entity.QPost.post;

@Slf4j
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public PostRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PostDto> findAllPosts(Pageable pageable) {
        QueryResults<PostDto> result = queryFactory
                .select(new QPostDto(
                        post.id,
                        post.title,
                        post.content,
                        member.username,
                        post.createdAt,
                        post.modifiedAt
                ))
                .from(post)
                .leftJoin(post.member, member)
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostDto> content = result.getResults();
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
    }



    @Override
    public Page<PostDto> findPostSearch(PostSearch search, Pageable pageable) {
        QueryResults<PostDto> result = queryFactory
                .select(new QPostDto(
                        post.id,
                        post.title,
                        post.content,
                        member.username,
                        post.createdAt,
                        post.modifiedAt
                ))
                .from(post)
                .leftJoin(post.member, member)
                .where(
                        titleContain(search.getTitle()),
                        contentContain(search.getContent()),
                        usernameContain(search.getUsername())
                )
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<PostDto> content = result.getResults();
        long total = result.getTotal();
        return new PageImpl<>(content, pageable, total);
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
