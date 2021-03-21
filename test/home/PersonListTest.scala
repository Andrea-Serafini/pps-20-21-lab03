package home

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import home.ListsExtended.List
import home.ListsExtended.List._
import u02.Modules.Person
import u02.Modules.Person._

class PersonListTest {

  val lst : List[Person] =
    Cons(Teacher("pippo", "latino"),
      Cons (Teacher("luca", "italiano"),
        Cons (Student("marco", 2001),
          Cons (Teacher("giulio", "arte"),
            Cons (Student("piero", 2001),
              Nil ()
            )
          )
        )
      )
    )


 @Test def testTeacherCourses(): Unit ={

   println(teacherCourses(lst))
   println(teacherCoursesCombination(lst))
   assertEquals(teacherCourses(lst),teacherCoursesCombination(lst))
 }

  @Test def testFold(): Unit ={
    val lst = Cons (3 , Cons (7 , Cons (1 , Cons (5 , Nil () ) ) ) )
    val lstEmpty : List[Int] = Nil()

    assertEquals(-16,foldLeft ( lst ) (0) (_ - _ ))
    assertEquals(-8,foldRight ( lst ) (0) (_ - _ ))
    assertEquals(10,foldLeft ( lstEmpty ) (10) (_ - _ ))
    assertEquals(10,foldRight ( lstEmpty ) (10) (_ - _ ))


  }
}
