package com.toy.basic.`02-scope-function`

/**
 * also 내에서 it로 수신객체에 접근 가능
 *
 * 긴 클래스명에 대한 간소화된 접근 가능
 */
fun main() {
  val veryLongNamesClass = VeryLongNamesClass(data1 = "data1", data2 = "data2")

  veryLongNamesClass.also {
    println("data1 length: ${it.data1}")
    println("data1 length: ${it.data2}")
  }
}

data class VeryLongNamesClass(
  var data1: String,
  var data2: String
)