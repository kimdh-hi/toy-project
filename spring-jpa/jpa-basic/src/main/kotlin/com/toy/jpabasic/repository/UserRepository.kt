package com.toy.jpabasic.repository

import com.toy.jpabasic.domain.User
import com.toy.jpabasic.vo.UserListResponseVO
import com.toy.jpabasic.vo.UserListV3ResponseVO
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun UserRepository.get(id: String)
  = findByIdOrNull(id) ?: throw RuntimeException("user not found ...")

interface UserRepository: CrudRepository<User, String>, UserRepositoryCustom

interface UserRepositoryCustom {

  fun searchList(): MutableList<UserListResponseVO>

  fun searchListV2(): MutableList<User>

  fun searchListV3(): MutableList<UserListV3ResponseVO>
}