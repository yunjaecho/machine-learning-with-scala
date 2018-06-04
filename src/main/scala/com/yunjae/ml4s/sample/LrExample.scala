package com.yunjae.ml4s.sample

import breeze.linalg.DenseMatrix
import breeze.numerics.pow
import breeze.stats.mean
import com.yunjae.ml4s.supervised.LinearRegression

import scala.io.Source

object LrExample extends App {

  def line2Data(line: String): Array[Double] = {
    line
      .split("\\s+")
      .filter(_.length > 0)
      .map(_.toDouble)
  }

  // import data
  val data = Source.fromFile("datasets/boston_housing.data")
    .getLines()
    .map(x => line2Data(x))
    .toArray

  // convert to breeze matrix
  val dm = DenseMatrix(data:_*)

  // the inputs are all but the last column. Outputs are last column
  val x = dm(::, 0 to 12)
  val y = dm(::, -1).toDenseMatrix.t

  // create LR object with our dataset
  val myLr = new LinearRegression(
    inputs = x,
    outputs = y
  )

  // Train LR weights
  val weights = myLr.train()

  val testX = x(0 to 30, ::)
  val testY = y(0 to 30, ::)

  val pred = myLr.predict(weights, testX)

  val mseEvaluator = (pred: DenseMatrix[Double], target: DenseMatrix[Double])
  => mean((pred - target).map(x => pow(x, 2)))

  val mse = myLr.evaluate(
    weights = weights,
    inputs = testX,
    targets = testY,
    evaluator = mseEvaluator
  )

  println(mse)
}
