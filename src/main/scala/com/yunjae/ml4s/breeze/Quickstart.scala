package com.yunjae.ml4s.breeze

import breeze.linalg._
import breeze.stats.mean

object Quickstart extends App {

  val x = DenseMatrix.zeros[Double](1, 5)
  println(x)
  x(0, 0) = 1
  x(0, 1) = 2
  println(x)

  /**
    * Breeze also supports slicing. Note that slices using a Range are much,
    * much faster than those with an arbitrary sequence.
    */
  x(::, 3 to 4) := 0.5
  println(x)

  /**
    * The slice operator constructs a read-through and write-through
    * view of the given elements in the underlying vector.
    * You set its values using the vectorized-set operator :=.
    * You could as well have set it to a compatibly sized Vector.
    */
  x(::, 0 to 1) := DenseVector(0.1, 0.2)
  println(x)

  /**
    * Similarly, a DenseMatrix can be created with a constructor method call,
    * and its elements can be accessed and updated.
    */

  val m = DenseMatrix.zeros[Int](5, 5)
  println(m)

  /**
    * The columns of m can be accessed as DenseVectors, and the rows as DenseMatrices
    */
  println((m.rows, m.cols))
  println(m(::, 1))

  m(4, ::) := DenseVector(1,2,3,4,5).t
  println(m)

  // requirement failed: Row dimension mismatch!: a.rows == b.rows (5 != 3)
  //m := DenseMatrix.zeros[Int](3,3)

  /**
    * Sub-matrices can be sliced and updated,
    * and literal matrices can be specified using a simple tuple-based syntax.
    * Unlike Scalala, only range slices are supported, and only the columns
    * (or rows for a transposed matrix) can have a Range step size different from 1.
    */
  m(0 to 1, 0 to 1) := DenseMatrix((3,1), (-1, -2))
  println(m)

  /**
    * Operators
    *
    * All Tensors support a set of operators, similar to those used in Matlab or Numpy.
    * See Linear Algebra Cheat-Sheet for a list of most of the operators and various operations.
    * Some of the basic ones are reproduced here, to give you an idea.
    * ============================================
    * Operation	Breeze	Matlab	Numpy
    * ============================================
    * Elementwise addition	a + b	a + b	a + b
    * Elementwise multiplication	a :* b	a .* b	a * b
    * Elementwise comparison	a :< b	a < b (gives matrix of 1/0 instead of true/false)	a < b
    * Inplace addition	a :+= 1.0	a += 1	a += 1
    * Inplace elementwise multiplication	a :*= 2.0	a *= 2	a *= 2
    * Vector dot product	a dot b,a.t * b†	dot(a,b)	dot(a,b)
    * Elementwise sum	sum(a)	sum(sum(a))	a.sum()
    * Elementwise max	a.max	max(a)	a.max()
    * Elementwise argmax	argmax(a)	argmax(a)	a.argmax()
    * Ceiling	ceil(a)	ceil(a)	ceil(a)
    * Floor	floor(a)	floor(a)	floor(a)
    */
  val dm = DenseMatrix((1.0,2.0,3.0),(4.0,5.0,6.0))

  val res = dm(::, *) + DenseVector(3.0, 4.0)
  println(res)

  res(::, *)  := DenseVector(3.0, 4.0)
  println(res)

  println(mean(dm(*, ::)))

  /**
    * breeze.stats.distributions
    *
    * Breeze also provides a fairly large number of probability distributions.
    * These come with access to probability density function for either discrete or continuous distributions.
    * Many distributions also have methods for giving the mean and the variance.
    */

  import breeze.stats.distributions._

  val poi = new Poisson(3.0)
  val s = poi.sample(5)
  println(s)

  val s2 = s map { poi.probabilityOf(_) }
  println(s2)

  // meanAndVariance requires doubles, but Poisson samples over Ints
  val doublePoi = for(x <- poi) yield x.toDouble
  println(doublePoi)

  breeze.stats.meanAndVariance(doublePoi.samples.take(1000))
  val res1 = (poi.mean, poi.variance)
  print(res1)
}
