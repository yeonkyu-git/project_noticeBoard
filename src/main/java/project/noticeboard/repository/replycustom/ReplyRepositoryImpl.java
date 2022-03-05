package project.noticeboard.repository.replycustom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import project.noticeboard.dto.QReplyDto;
import project.noticeboard.dto.ReplyDto;
import project.noticeboard.entity.Post;
import project.noticeboard.entity.QMember;
import project.noticeboard.entity.QPost;
import project.noticeboard.entity.QReply;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static project.noticeboard.entity.QMember.member;
import static project.noticeboard.entity.QPost.post;
import static project.noticeboard.entity.QReply.reply;

@Slf4j
public class ReplyRepositoryImpl implements ReplyRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ReplyRepositoryImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }



    @Override
    public List<ReplyDto> findAllReplyByPost(Post post) {
        return queryFactory
                .select(new QReplyDto(
                        reply.id,
                        member.id,
                        reply.content,
                        member.username,
                        reply.createdAt
                ))
                .from(reply)
                .leftJoin(reply.member, member)
                .where(reply.post.id.eq(post.getId()))
                .orderBy(reply.createdAt.desc())
                .fetch();
    }
}
