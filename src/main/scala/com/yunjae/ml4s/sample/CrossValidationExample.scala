package com.yunjae.ml4s.sample

import breeze.linalg.DenseMatrix
import breeze.plot.{Figure, _}
import breeze.numerics._
import breeze.stats.mean
import com.yunjae.ml4s.supervised.LinearRegression

import scala.io.Source

object CrossValidationExample extends App {

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

  // the inputs are all but the last column. outputs are last column
  val x = dm(::, 0 to 12)
  val y = dm(::, -1).toDenseMatrix.t

  println(dm(::, -1))
  println("=============================")
  println(dm(::, -1).toDenseMatrix)
  println("=============================")
  println(dm(::, -1).toDenseMatrix.t)

  val mseEvaluator = (pred: DenseMatrix[Double], target: DenseMatrix[Double])
  => mean((pred - target).map(x => pow(x, 2)))

  // create LR object with out dataset
  val myLr = new LinearRegression(
    inputs = x,
    outputs = y
  )

  var i = 0.000001

  var errors = Vector.empty[Double]
  var paramVals = Vector.empty[Double]
  var counter = 0

  while(i < 1000) {
    val cvError = myLr.crossValidation(
      folds = 5,
      regularizationParam = i,
      evaluator = mseEvaluator
    )

    i *= 10

    counter +=1
    errors :+= cvError
    paramVals :+= counter.toDouble
  }

  println(errors.size)
  println(paramVals.size)

  val f = Figure()
  val p = f.subplot(0)

  p += plot(paramVals.toIndexedSeq, errors.toIndexedSeq)
}
