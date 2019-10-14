package ostrat

trait ArrayBased[+A] extends Any
{ def length: Int
  def apply(index: Int): A

  def head: A = apply(0)
  def last: A = apply(length - 1)

  def foreach[U](f: A => U): Unit =
  { var count = 0
    while(count < length)
    { f(apply(count))
      count = count + 1
    }
  }

  /** foreach with index. The startIndex parameter is placed 2nd to allow it to have a default value of zero. */
  def iForeach[U](f: (A, Int) => U, startIndex: Int = 0): Unit =
  { val endIndex = length + startIndex
    var i: Int = startIndex
    while(i < endIndex ) { f(apply(i), i); i = i + 1 }
  }

  def map[B](f: A => B)(implicit ev: ArrBuilder[B]): ev.ImutT =
  { val res = ev.imutNew(length)
    iForeach((a, i) => ev.imutSet(res, i, f(a)))
    res
  }

  def seqMap[B](f: A => Iterable[B])(implicit ev: ArrBuilder[B]): ev.ImutT =
  { val buff = ev.buffNew(length)
    foreach(a => ev.buffAppendSeq(buff, f(a)))
    ev.buffImut(buff)
  }
}

object ArrayBased
{
  implicit class ArrBaseImplicit[A](ba: ArrayBased[A])
  {
    def bind[BB <: ArrImut[_]](f: A => BB)(implicit ev: Bind[BB]): BB = ev.bind[A](ba, f)
  }
}