package u03

import u03.Streams.Stream

object Streams {
  import Lists._
  sealed trait Stream[A]

  object Stream {
    private case class Empty[A]() extends Stream[A]
    private case class Cons[A](head: () => A, tail: () => Stream[A]) extends Stream[A]

    def empty[A](): Stream[A] = Empty()

    def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
      lazy val head = hd
      lazy val tail = tl
      Cons(() => head, () => tail)
    }

    def toList[A](stream: Stream[A]): List[A] = stream match {
      case Cons(h,t) => List.Cons(h(), toList(t()))
      case _ => List.Nil()
    }

    def map[A, B](stream: Stream[A])(f: A => B): Stream[B] = stream match {
      case Cons(head, tail) => cons(f(head()), map(tail())(f))
      case _ => Empty()
    }

    def filter[A](stream: Stream[A])(pred: A => Boolean): Stream[A] = stream match {
      case Cons(head, tail) if (pred(head())) => cons(head(), filter(tail())(pred))
      case Cons(head, tail) => filter(tail())(pred)
      case _ => Empty()
    }

    def take[A](stream: Stream[A])(n: Int): Stream[A] = (stream,n) match {
      case (Cons(head, tail), n) if n>0 => cons(head(), take(tail())(n - 1))
      case _ => Empty()
    }

    //def iterate[A](init: => A)(next: A => A): Stream[A] = cons(init, iterate(next(init))(next))

    def takeWhile[A](stream: Stream[A])(pred: A => Boolean): Stream[A] = stream match {
      case Cons(head, tail) if pred(head()) => cons(head(), takeWhile(tail())(pred))
      case _ => Empty()
    }

    def peek[A](stream: Stream[A])(exec: A => Unit): Stream[A] = map(stream)(x => {exec(x);x})

    def fold[A, B](stream: Stream[A])(base: => B)( op: (A, B) => B): B = stream match {
      case Cons(head, tail) => op(head(), fold(tail())(base)(op))
      case Empty() => base
    }

    def iterate[A](init: => A)(next: A => A): Stream[A] = cons(init,iterate(next(init))(next))

    def generate[A](seed: => A): Stream[A] = iterate(seed)(x => x)

    def drop[A](stream: Stream[A])(n: Int): Stream[A] = (stream,n) match {
      case (Cons(head, tail), n) if n>0 => drop(tail())(n - 1)
      case (Cons(head, tail), 0) => Cons(head, tail)
      case _ => Empty()
    }

    def constant[A](item: A) : Stream[A] = iterate(item)(item=>item)

    def fib_tail(n: Int, a: Int, b: Int): Int = n match {
      case 0 => a
      case _ => fib_tail(n - 1, b, a + b)
    }

    val fibs: Stream[Int] = map(iterate(0)(_ + 1))(fib_tail(_,0,1))

    val fibsOptimized: Stream[Int] ={
      def fib(prev: Int, curr: Int): Stream[Int] = cons(prev,fib(curr, prev + curr))
      fib(0, 1)
    }
  }
}

object StreamsMain extends App {

  import Stream._
  var s = cons(10,cons(11, cons(20, empty())));
  println("s: " + s)
  println("tolist: " + toList(s))
  println("map: " + toList(map(s)(_+1)))
  println("takewhile: " + takeWhile(s)(_<15))
  println("peek: " + toList(peek(s)(println(_))))
  println("fold: " + fold[Int,Int](s)(0)(_+_))
  println(toList(takeWhile(iterate(0)(_+1))(_<50)))
  println(toList(takeWhile(generate(Math.random()))(_>=0.1)))

  /*
  // var simplifies chaining of functions a bit..
  var str = Stream.iterate(0)(_+1)   // {0,1,2,3,..}
  str = Stream.map(str)(_+1)    // {1,2,3,4,..}
  str = Stream.filter(str)(x => (x < 3 || x > 20)) // {1,2,21,22,..}
  str = Stream.take(str)(10) // {1,2,21,22,..,28}
  println(Stream.toList(str)) // [1,2,21,22,..,28]

  val corec: Stream[Int] = Stream.cons(1, corec) // {1,1,1,..}
  println(Stream.toList(Stream.take(corec)(10))) // [1,1,..,1]*/
}
