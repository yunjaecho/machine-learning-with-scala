package com.yunjae.ml4s.sample

import java.awt.{Color, Paint}

import breeze.linalg.{DenseVector, sum}
import breeze.plot._
import com.yunjae.ml4s.unsupervised.KMeans

import scala.io.Source

object KMeansExample extends App {

  def toDoble(s: String): Option[Double] = {
    try {
      Some(s.toDouble)
    } catch {
      case e: Exception => None
    }
  }

  val srDataset = Source.fromFile("datasets/311_Service_Requests_for_2009.csv")
    .getLines()
    .map(line => line.split(","))
    .filter(_(5) == "Noise")
    .filter { splitLine =>
      splitLine.length match {
        case 53  => (toDoble(splitLine(24)) != None) && (toDoble(splitLine(25)) != None)
        case 54  => (toDoble(splitLine(25)) != None) && (toDoble(splitLine(26)) != None)
        case _ => false
      }
    }
    .map { splitLine =>
      if (splitLine.length == 53) DenseVector(splitLine(24).toDouble, splitLine(25).toDouble)
      else DenseVector(splitLine(25).toDouble, splitLine(26).toDouble)
    }
    .toSeq


  val f = Figure()

  val euclideanDistance =
    (dp1: DenseVector[Double], dp2: DenseVector[Double]) =>
      sum((dp1 - dp2).map(e1 => e1 * e1))

  val clusters = KMeans.cluster(dataset = srDataset,
    numClusters = 6,
    distanceFunc = euclideanDistance)

  val id2Color: Int => Paint = id => id match {
    case 0 => Color.YELLOW
    case 1 => Color.RED
    case 2 => Color.GREEN
    case 3 => Color.BLUE
    case 4 => Color.GRAY
    case _ => Color.BLACK
  }

  f.subplot(0).xlabel = "X-coordinate"
  f.subplot(0).ylabel = "Y-coordinate"
  f.subplot(0).title = "311 Service Noise Complaints"

  clusters.zipWithIndex.foreach { case (cl, clIdx) =>
    val clusterX = clusters(clIdx).assignedDataPoint.map(_(0))
    val clusterY = clusters(clIdx).assignedDataPoint.map(_(1))
    f.subplot(0) += scatter(clusterX, clusterY, {(_: Int) => 1000}, {(_: Int) => id2Color(clIdx)})
  }
}
