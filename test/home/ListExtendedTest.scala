package home

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import home.ListsExtended.List._

class ListExtendedTest {


  @Test def testDrop(){
    val lst = Cons (10 , Cons (20 , Cons (30 , Nil () )) )

    assertEquals(Cons (20 , Cons (30 , Nil ())) ,drop( lst ,1))
    assertEquals(Cons (30 , Nil ()) ,drop( lst ,2))
    assertEquals(Nil () ,drop( lst ,5))

  }

}
