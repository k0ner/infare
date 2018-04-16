case class Timer(start: Long) {

  def stop(): Long = (System.nanoTime() - start) / 1000000
}

object Timer {

  def start() = Timer(System.nanoTime())
}
