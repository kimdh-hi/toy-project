package com.toy.webfluxr2dbcpostgres.domain

import com.toy.webfluxr2dbcpostgres.common.PasswordUtils
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.io.Serial

@Table(name = "tb_user")
class User (
  @field:Id
  var id: Long? = null,
  var name: String,
  var username: String,
  var password: String,
//  var companyId: Long,
): AbstractDateTraceEntity() {
  companion object {
    @Serial
    private const val serialVersionUID: Long = -6590154582899094859L

    fun newUser(name: String, username: String, password: String): User {
      return User(
        name = name, username = username, password = PasswordUtils.encode(password)
      )
    }
  }

  fun update(
    name: String?, username: String?, password: String?
  ) {
    name?.let { this.name = it }
    username?.let { this.username = it }
    password?.let { this.password = it }
  }

  fun checkPassword(password: String) {
    if (!PasswordUtils.matches(password, this.password)) throw IllegalArgumentException("failed to login ...")
  }
}