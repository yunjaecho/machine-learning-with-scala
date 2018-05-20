package com.yunjae.ml4s.exploratory

import org.saddle.{Mat, mat}


object Mat_Sample extends App {
  /**
    * Map is the matrix class provided by Saddle.
    * It is the equivalent of a two-dimensional array.
    * The Mat instances store their data as contiguous arrays in memory.
    * You can create a Mat object by supplying an array as well as the shape of the matrix.
    * Shape refer to the number of rows and columns in the matrix.
    */


  val m = Mat(2,3, Array(1,1,2,3,5,8))
  print(m)

  /**
    * If you want an identity matrix, an empty matrix, or a matrix filled with zeros,
    * then the following three cases would be of help
    */

  val m2 = mat.ident(2)
  println(m2)

  val m3 = mat.ones(2,3)
  println(m3)

  val m4 = mat.zeros(2,3)
  println(m4)


  /**
    * Finally, you may wish to initialize a matrix with random numbers,
    * This is done similarly to how it is done with the Vec class.
    * Use the rand method of the singleton object mat to get matrices filled with uniformly distributed numbers.
    * The randn() method will give you a matrix filled with normally distributed numbers with mean
    * 0.0 and standard deviation 1.0. the randn2() method will initialize the matrix with random values from the
    * normal distribution with specified mean and standard deviation.
    */
  val m5 = mat.rand(2,2)
  println(m5)

  println("randn")
  val m6 = mat.randn(2,2)
  println(m6)
}
