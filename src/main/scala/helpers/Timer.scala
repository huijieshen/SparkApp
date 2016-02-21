package babylon.helpers

/**
  * Created by mrez on 2/16/16.
  */
object Timer {

  def time[T](descr: String)(f: => T): T = {
    val start = System.nanoTime
    val r = f
    val end = System.nanoTime
    val time = (end - start) / 1e6
    println(descr + ": time = " + time + "ms")
    r
  }

}
