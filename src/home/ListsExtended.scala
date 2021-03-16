package home

object ListsExtended {

  // A generic linkedlist
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

    def map[A,B](l: List[A])(mapper: A=>B): List[B] = l match {
      case Cons(h, t) => Cons(mapper(h), map(t)(mapper))
      case Nil() => Nil()
    }

    def filter[A](l1: List[A])(pred: A=>Boolean): List[A] = l1 match {
      case Cons(h,t) if (pred(h)) => Cons(h, filter(t)(pred))
      case Cons(_,t) => filter(t)(pred)
      case Nil() => Nil()
    }

    def drop[A](l: List[A], n: Int): List[A] = l match {
      case Cons(h, t) if (n > 0) => drop(t, n-1)
      case Cons(h,t) if (n == 0) => Cons(h, t)
      case _ => Nil()
    }
  }


}
