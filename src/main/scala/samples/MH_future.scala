/**
  * Created by huijie on 2016-02-19.
  * metropolis-hastings using future
  * adapted from https://darrenjw.wordpress.com/2010/08/15/metropolis-hastings-mcmc-algorithms/
  */

import java.util.concurrent.ThreadLocalRandom

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object MH_future extends App{
  val start = System.currentTimeMillis()

  def dnorm(x: Double): Double = {
    math.exp(-(x * x / 2)) / math.sqrt(2 * java.lang.Math.PI)
  }

  val mh: Seq[Future[Array[Double]]] = for (i <- 1 to 4) yield Future {
  val n = 25000000 //when set to 100000000, memory not enough
    val eps = 1

    val vec = ArrayBuffer.empty[Double]
    var x = 0.0
    var oldll = math.log(dnorm(x))

    vec += x

    var i = 0
    for (i <- 1 to (n - 1)) {
      val innov = ThreadLocalRandom.current().nextDouble() * 2 - eps
      val can = x + innov
      val loglik = math.log(dnorm(can))
      val loga = loglik - oldll
      if (math.log(ThreadLocalRandom.current().nextDouble()) < loga) {
        x = can
        oldll = loglik
      }
      vec += x
    }
    vec.toArray
  }

  val aggregated: Future[Seq[Array[Double]]] = Future.sequence(mh)

  val squares: Seq[Array[Double]] = Await.result(aggregated, Duration.Inf)
  val result = squares.flatten

  //println(result)

  println("time: " + (System.currentTimeMillis() - start) + " ms")

}