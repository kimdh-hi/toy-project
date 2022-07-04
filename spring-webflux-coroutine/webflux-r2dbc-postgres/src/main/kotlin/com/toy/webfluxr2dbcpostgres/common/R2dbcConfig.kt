package com.toy.webfluxr2dbcpostgres.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.security.core.context.ReactiveSecurityContextHolder

@Configuration
@EnableR2dbcAuditing
class R2dbcConfig {

  @Bean
  fun reactiveAuditorAware(): ReactiveAuditorAware<String> {
    return ReactiveAuditorAware<String> {
      ReactiveSecurityContextHolder.getContext()
        .map { it.authentication }
        .filter { it.isAuthenticated }
        .map { it.name }
    }
  }
}