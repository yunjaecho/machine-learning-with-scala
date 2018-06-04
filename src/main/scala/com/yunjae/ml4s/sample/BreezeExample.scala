package com.yunjae.ml4s.sample

import breeze.linalg.DenseMatrix

object BreezeExample extends App {
  // create a denseMatrix
  val dm1 = DenseMatrix((1.0, 2.0), (3.0, 4.0))

  // matrix transpose
  val dmlTranspose = dm1.t
  println(s"${dm1} transposed is ${dmlTranspose}")
  println("========================================")

  // create a second denseMatrix
  val dm2 = DenseMatrix((5.0, 6.0), (7.0, 8.0))

  // matrix product
  // https://ko.wikipedia.org/wiki/행렬_곱셈
  val matrixProduct = dm1 * dm2
  println(s"The product of ${dm1} and ${dm2} is ${matrixProduct}")
  println("========================================")

  //matrix elementwise sum
  val matrixElSum = dm1:+dm2
  println(s"The elementwise sum ${matrixElSum}")


}
