package home

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import u03.Streams.Stream
import u03.Lists.List.{Cons, Nil}
import u03.Streams.Stream.{fibs, fibsOptimized}


class StreamTest {

  val s = Stream.take(Stream.iterate(0)(_ + 1))(10)


  @Test def testDrop(): Unit = {

    assertEquals(Cons(6, Cons(7, Cons(8, Cons(9, Nil())))),
      Stream.toList(Stream.drop(s)(6)))


    assertEquals(Nil(), Stream.toList(Stream.drop(s)(10)))
    assertEquals(Nil(), Stream.toList(Stream.drop(s)(13)))
  }

  @Test def testConstant(): Unit = {
    val stream = Stream.constant("x")
    assertEquals(Cons("x", Cons("x", Cons("x", Cons("x", Cons("x", Nil()))))),
      Stream.toList(Stream.take(stream)(5)))
  }

  @Test def testFib(): Unit = {

    assertEquals(Cons(0, Cons(1, Cons(1, Cons(2, Cons(3, Cons(5, Cons(8, Cons(13, Nil())))))))),
      Stream.toList(Stream.take(fibs)(8)))
    assertEquals(Cons(0, Cons(1, Cons(1, Cons(2, Cons(3, Cons(5, Cons(8, Cons(13, Nil())))))))),
      Stream.toList(Stream.take(fibsOptimized)(8)))

  }
}
