package com.toy.webfluxr2dbcpostgres.domain

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.relational.core.mapping.Column
import java.io.Serial
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.EntityListeners

abstract class AbstractBaseEntity(): Serializable {
  companion object {
    @Serial
    private const val serialVersionUID: Long = 1527053557977149325L

  }
}

@EntityListeners(AuditingEntityListener::class)
abstract class AbstractDateTraceEntity(
  @Column("created_date")
  @CreatedDate
  var createdDate: LocalDateTime? = null,

  @Column("updated_date")
  @LastModifiedDate
  var updatedDate: LocalDateTime? = null
): AbstractBaseEntity() {
  companion object {
    @Serial
    private const val serialVersionUID: Long = -3311921908045215847L
  }
}

@EntityListeners(AuditingEntityListener::class)
abstract class AbstractByTraceEntity(
  @Column("created_by")
  @CreatedBy
  var createdBy: String? = null,

  @Column("updated_by")
  @LastModifiedBy
  var updatedBy: String? = null
): AbstractBaseEntity() {
  companion object {
    @Serial
    private const val serialVersionUID: Long = -3311921908045215847L
  }
}