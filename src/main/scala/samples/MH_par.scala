import scala.collection.mutable.ArrayBuffer
import java.util.concurrent.ThreadLocalRandom
/**
  * Created by huijie on 2016-02-18.
  * metropolis-hastings using par
  * adapted from https://darrenjw.wordpress.com/2010/08/15/metropolis-hastings-mcmc-algorithms/
  */
object MH_par{
  //psudo-code:
  //  metrop3=function(n=1000,eps=0.5)
  //  {
  //    vec=vector("numeric", n)
  //    x=0
  //    oldll=dnorm(x,log=TRUE)
  //    vec[1]=x
  //    for (i in 2:n) {
  //    can=x+runif(1,-eps,eps)
  //    loglik=dnorm(can,log=TRUE)
  //    loga=loglik-oldll
  //    if (log(runif(1)) < loga) {
  //      x=can
  //      oldll=loglik
  //    }
  //    vec[i]=x
  //  }
  //    vec
  //  }


  def dnorm(x: Double): Double = {
    math.exp(-(x * x / 2)) / math.sqrt(2 * java.lang.Math.PI)
  }

  def mh(n: Int): ArrayBuffer[Double] = {
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
    vec
  }

  def main(args: Array[String]) = {
    val start = System.currentTimeMillis()

    val N=4
    val iters=100000000
    val its=iters/N
    val parts=(1 to N).toArray.par map {x => mh(its)}
    val result=parts.flatten
    //println(result)

    println("time: " + (System.currentTimeMillis()-start) + " ms")
  }
}
