package com.toy.webfluxr2dbcpostgres.controller

import com.toy.webfluxr2dbcpostgres.base.AbstractIntegrationTest
import com.toy.webfluxr2dbcpostgres.base.TestData
import com.toy.webfluxr2dbcpostgres.vo.UserSaveRequestVO
import com.toy.webfluxr2dbcpostgres.vo.UserSaveResponseVO
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import reactor.blockhound.BlockHound

internal class UserControllerTest: AbstractIntegrationTest() {

  @Test
  fun `save`() = runTest {
    //given
    val requestVO = UserSaveRequestVO(name = "test-name", username = "test-username", password = "test1234")
    val token = jwtUtil.createToken(TestData.USER)

    //expect
    webTestClient.post()
      .uri("/api/users")
      .header(HttpHeaders.AUTHORIZATION, token)
      .contentType(MediaType.APPLICATION_JSON)
      .body(BodyInserters.fromValue(requestVO))
      .exchange()
      .expectStatus().isOk
      .expectBody(UserSaveResponseVO::class.java)

  }

  @Test
  fun `get`() = runTest {

    BlockHound.install()

    //given
    val token = jwtUtil.createToken(TestData.USER)

//    Thread.sleep(1000L)
    //expect
    webTestClient.get()
      .uri("/api/users")
      .header(HttpHeaders.AUTHORIZATION, token)
      .exchange()
      .expectStatus().isOk

  }
}