package home

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import u03.Streams.Stream
import u03.Lists.List.{Cons, Nil}




class StreamTest {

  val s = Stream.take(Stream.iterate(0)( _ +1) ) (10)


  @Test def testDrop(): Unit ={

    assertEquals(Cons (6 , Cons (7 , Cons (8 , Cons (9 , Nil ()))))
      ,Stream.toList(Stream.drop ( s ) (6)))


    assertEquals(Nil()
      ,Stream.toList(Stream.drop ( s ) (10)))

    assertEquals(Nil()
      ,Stream.toList(Stream.drop ( s ) (13)))
  }

  @Test def testConstant(): Unit = {
    val stream = Stream.constant ("x")
    assertEquals( Cons ("x", Cons ("x", Cons ("x", Cons ("x", Cons ("x", Nil())))))
      ,Stream.toList( Stream.take(stream)(5)))
  }

}
