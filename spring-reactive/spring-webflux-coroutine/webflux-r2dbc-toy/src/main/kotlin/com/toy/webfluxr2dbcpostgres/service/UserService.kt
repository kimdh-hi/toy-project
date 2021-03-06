package com.toy.webfluxr2dbcpostgres.service

import com.toy.webfluxr2dbcpostgres.auth.JwtPrincipal
import com.toy.webfluxr2dbcpostgres.auth.JwtUtil
import com.toy.webfluxr2dbcpostgres.domain.User
import com.toy.webfluxr2dbcpostgres.repository.UserRepository
import com.toy.webfluxr2dbcpostgres.vo.LoginRequestVO
import com.toy.webfluxr2dbcpostgres.vo.UserSaveRequestVO
import com.toy.webfluxr2dbcpostgres.vo.UserSaveResponseVO
import com.toy.webfluxr2dbcpostgres.vo.UserUpdateRequestVO
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class UserService(
  private val userRepository: UserRepository,
  private val jwtUtil: JwtUtil
) {

  suspend fun get(id: Long): User {
    return userRepository.findById(id) ?: throw IllegalArgumentException("user not found ...")
  }

  fun list(): Flow<User> {

    return userRepository.findAll()
  }

  suspend fun save(requestVO: UserSaveRequestVO): UserSaveResponseVO {
    if(userRepository.existsByUsername(requestVO.username))
      throw IllegalArgumentException("username is duplicated ...")
    val user = requestVO.toEntity()
    val savedUser = userRepository.save(user)
    return UserSaveResponseVO.of(savedUser)
  }

  suspend fun update(id: Long, requestVO: UserUpdateRequestVO) {
    val user = get(id)
    user.update(requestVO.name, requestVO.username, requestVO.password)

    userRepository.save(user)
  }

  suspend fun delete(id: Long) {
    val user = get(id)
    userRepository.deleteById(user.id!!)
  }

  suspend fun login(requestVO: LoginRequestVO): String {
    val user = userRepository.findByUsername(requestVO.username) ?: throw IllegalArgumentException("user not found ...")
    user.checkPassword(requestVO.password)

    return jwtUtil.createToken(user)
  }
}