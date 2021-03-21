package home

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import u03.Streams.Stream
import u03.Streams.Stream._



class StreamTest {

  val s = Stream.take(Stream.iterate(0)( _ +1) ) (10)


  @Test def testDrop(): Unit ={

    assertEquals(Stream.toList(cons(6, cons (7 , cons (8 , cons (9 , empty ())))))
      ,Stream.toList(Stream.drop ( s ) (6)))


    assertEquals(Stream.toList(empty ())
      ,Stream.toList(Stream.drop ( s ) (10)))

    assertEquals(Stream.toList(empty ())
      ,Stream.toList(Stream.drop ( s ) (13)))
  }
}
