package home

import u02.Modules.Person
import u02.Optionals.Option
import u02.Optionals.Option._
import u02.Modules.Person._

import scala.annotation.tailrec



object ListsExtended {

  // A generic linked list
  sealed trait List[E]

  // a companion object (i.e., module) for List
  object List {
    case class Cons[E](head: E, tail: List[E]) extends List[E]
    case class Nil[E]() extends List[E]

    def sum(l: List[Int]): Int = l match {
      case Cons(h, t) => h + sum(t)
      case _ => 0
    }

    def append[A](l1: List[A], l2: List[A]): List[A] = (l1, l2) match {
      case (Cons(h, t), l2) => Cons(h, append(t, l2))
      case _ => l2
    }

    def map[A,B](l: List[A])(mapper: A=>B): List[B] = flatMap(l)(x => Cons(mapper(x),Nil()))

    def filter[A](l1: List[A])(pred: A=>Boolean): List[A] = flatMap(l1)(x => if(pred(x)) Cons(x,Nil()) else Nil())

    @tailrec
    def drop[A](l: List[A], n: Int): List[A] = l match {
      case Cons(_, t) if n > 0 => drop(t, n-1)
      case Cons(h,t) if n == 0 => Cons(h, t)
      case _ => Nil()
    }

    def flatMap[A,B](l: List[A])(f: A => List[B]): List[B] = l match {
      case Cons(h, t) => append(f(h),flatMap(t)(f))
      case Nil() => Nil()
    }

    def max(l: List[Int]): Option[Int] = l match {
      case Cons(head, tail) => if(head > (max(tail) match {
        case Some(num) => num
        case None() => Int.MinValue
      })) Some(head) else Some(max(tail) match {
        case Some(a) => a
        case None() => Int.MinValue
      })
      case Nil() => None()
    }

    def teacherCourses(list: List[Person]): List[String] = flatMap(list) ({
      case Teacher(_, course) => Cons(course, Nil())
      case _ => Nil()
    })

    def teacherCoursesCombination(list: List[Person]): List[String] = filter(map(list) {
      case Teacher(_, course) => course
      case _ => "empty"
    })(_ != "empty")


  }


}
