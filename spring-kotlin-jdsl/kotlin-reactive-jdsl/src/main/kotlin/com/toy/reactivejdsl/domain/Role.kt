package com.toy.reactivejdsl.domain

import com.toy.reactivejdsl.domain.Authority
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "role")
class Role(
  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @GeneratedValue(generator = "uuid")
  var id: String,
  var name: String? = null,

  @ElementCollection(targetClass = Authority::class, fetch = FetchType.LAZY)
  @JoinTable(name ="role_authorities", joinColumns = [JoinColumn(name = "role_id")])
  @Column(name = "authority", nullable = false)
  @Enumerated(EnumType.STRING)
  val authorities: Set<Authority> = mutableSetOf()
): BaseEntity() {
  companion object {
    fun of(id: String) = Role(id = id)
  }

  override fun getPk(): Any? = id

  override fun getType(): Any? = id::class

  fun getAuthority(): Authority {
    val admin = authorities.firstOrNull { authority -> Authority.ADMIN == authority }
    return admin ?: Authority.USER
  }
}