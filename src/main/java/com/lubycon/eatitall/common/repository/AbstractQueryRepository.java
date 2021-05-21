package com.lubycon.eatitall.common.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractQueryRepository {
  private JPAQueryFactory queryFactory;

  @Autowired
  public void setQueryFactory(EntityManager entityManager) {
    this.queryFactory = new JPAQueryFactory(entityManager);
  }

  protected <T> JPAQuery<T> select(Expression<T> expr) {
    return queryFactory.select(expr);
  }
}
