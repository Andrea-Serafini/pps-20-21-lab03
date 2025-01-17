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
      case _ => l
    }

    def flatMap[A,B](l: List[A])(f: A => List[B]): List[B] = l match {
      case Cons(h, t) => append(f(h),flatMap(t)(f))
      case Nil() => Nil()
    }

    def max(l: List[Int]): Option[Int] = l match {
      case Cons(head, tail) =>Some(foldLeft(l)(Int.MinValue)(greater(_,_)))
      case Nil() => None()
    }

    private def greater(a: Int, b: Int): Int = if(a > b) a else b

    def teacherCourses(list: List[Person]): List[String] = flatMap(list) ({
      case Teacher(_, course) => Cons(course, Nil())
      case _ => Nil()
    })

    def teacherCoursesCombination(list: List[Person]): List[String] = filter(map(list) {
      case Teacher(_, course) => course
      case _ => "empty"
    })(_ != "empty")

    @tailrec
    def foldLeft[A,B](list: List[A])(default: B)(operator: (B,A) => B): B = list match {
      case Cons(head, tail) => foldLeft(tail)(operator(default,head))(operator)
      case _ => default
    }

    def foldRight[A,B](list: List[A])(default: B)(operator: (A, B) => B): B = list match {
      case Cons(head, tail) => operator(head, foldRight(tail)(default)(operator))
      case _ => default
    }

    @tailrec
    def reverse[A](list: List[A], dest: List[A]): List[A] = list match {
      case Cons(head,tail) => reverse(tail, append(Cons(head,Nil()), dest))
      case _ => dest
    }
  }


}
