package com.ssafy.birdchain.common.db.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.birdchain.common.db.dto.response.comment.CommentAllResDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ssafy.birdchain.common.db.entity.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CommentAllResDTO> findAllByMarkerId(Long markerId) {
        QueryResults<CommentAllResDTO> results = jpaQueryFactory
                .select(Projections.constructor(CommentAllResDTO.class
                        , comment.id
                        , comment.nickname
                        , comment.content
                        , comment.createdAt
                ))
                .from(comment)
                .where(comment.status.eq(true), comment.marker.id.eq(markerId))
                .orderBy(comment.createdAt.desc())
                .fetchResults();
        return results.getResults();
    }

}
