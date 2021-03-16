package home

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import home.ListsExtended.List._

class ListExtendedTest {


  val lst = Cons (10 , Cons (20 , Cons (30 , Nil () )) )

  @Test def testDrop(){

    assertEquals(Cons (20 , Cons (30 , Nil ())) ,drop( lst ,1))
    assertEquals(Cons (30 , Nil ()) ,drop( lst ,2))
    assertEquals(Nil () ,drop( lst ,5))

  }

  @Test def testFlatMap(){

    assertEquals(Cons (11 , Cons (21 , Cons (31 , Nil ()))) , flatMap ( lst )(v => Cons ( v +1 , Nil () )))
    assertEquals(Cons (11 , Cons (12 , Cons (21 , Cons (22 , Cons (31 , Cons (32 , Nil ())))))) ,flatMap ( lst )(v => Cons ( v +1 , Cons (v +2 , Nil () ))))

  }

}
