package com.toy.springquerydsl.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import com.toy.springquerydsl.domain.Member
import com.toy.springquerydsl.domain.QMember.*
import com.toy.springquerydsl.vo.MemberDetailsResponseVO
import com.toy.springquerydsl.vo.MemberResponseVO
import com.toy.springquerydsl.vo.MemberSearchVO
import com.toy.springquerydsl.vo.QMemberResponseVO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.util.StringUtils

interface MemberRepository: CrudRepository<Member, String>, MemberRepositoryCustom

interface MemberRepositoryCustom {
  fun searchV0deprecated(pageable: Pageable, searchVO: MemberSearchVO): Page<MemberResponseVO>
  fun searchV1deprecated(pageable: Pageable, searchVO: MemberSearchVO): Page<MemberResponseVO>
}

class MemberRepositoryImpl(
  val query: JPAQueryFactory
): MemberRepositoryCustom {

  override fun searchV0deprecated(pageable: Pageable, searchVO: MemberSearchVO): Page<MemberResponseVO> {
    val content = getContents(searchVO, pageable)
    val totalCount = getTotalCount(searchVO, pageable)

    return PageImpl(content, pageable, totalCount)
  }

  override fun searchV1deprecated(pageable: Pageable, searchVO: MemberSearchVO): Page<MemberResponseVO> {
    val content = getContents(searchVO, pageable)

    val countQuery = query.select(QMemberResponseVO(
      member.username, member.age
    ))
      .from(member)
      .where(memberSearchCondition(searchVO))
      .limit(pageable.pageSize.toLong())
      .offset(pageable.offset)

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
  }

  private fun getContents(
    searchVO: MemberSearchVO,
    pageable: Pageable
  ): MutableList<MemberResponseVO> {
    val content = query.select(
      QMemberResponseVO(
        member.username, member.age
      )
    )
      .from(member)
      .where(memberSearchCondition(searchVO))
      .limit(pageable.pageSize.toLong())
      .offset(pageable.offset)
      .fetch()
    return content
  }

  private fun getTotalCount(
    searchVO: MemberSearchVO,
    pageable: Pageable
  ): Long {
    val totalCount = query.select(
      QMemberResponseVO(
        member.username, member.age
      )
    )
      .from(member)
      .where(memberSearchCondition(searchVO))
      .limit(pageable.pageSize.toLong())
      .offset(pageable.offset)
      .fetchCount()
    return totalCount
  }

  private fun memberSearchCondition(searchVO: MemberSearchVO): BooleanBuilder {
    val builder = BooleanBuilder()

    if (StringUtils.hasText(searchVO.username))
      builder.and(member.username.containsIgnoreCase(searchVO.username))

    searchVO.age?.let {
      builder.and(member.age.between(it, it + 10))
    }

    return builder
  }
}

