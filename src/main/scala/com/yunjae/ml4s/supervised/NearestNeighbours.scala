package com.yunjae.ml4s.supervised

import breeze.linalg.{*, DenseMatrix, DenseVector}

/**
  * An implementation of the k-nearest neighbours classification algorithm.
  *
  * @param k The number of neighbours to use for prediction (가까운 기준 몇개)
  * @param dataX Matrix of input examples (Train 데이터)
  * @param dataY Corresponding output classes(일치하는 값 : Label 값)
  * @param distanceFn
  */
class NearestNeighbours(k: Int,
                        dataX: DenseMatrix[Double],
                        dataY: Seq[String],
                        distanceFn: (DenseVector[Double], DenseVector[Double]) => Double) {

  /**
    * Predict the output class corresponding to a given input example
    * @param x input example
    * @return predicated class
    */
  def predict(x : DenseVector[Double]): String = {
    // compute similarity for each example
    val distances = dataX(*, ::).map(r => distanceFn(r, x))

    // Get tok k most similar classes
    val topKClasses = distances
      .toArray
      .zipWithIndex
      .sortBy(_._1)
      .take(k)
      .map {
        case (dist, idx) => dataY(idx)
      }

    // Most frequent class in top k
    topKClasses
      .groupBy(identity)
      .mapValues(_.size)
      .maxBy(_._2)._1
  }

}
