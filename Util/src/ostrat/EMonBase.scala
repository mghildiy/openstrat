package ostrat

/** This is the base trait for the specialisations of Error Monads. Each specialisation eg EMonInt has its own specialised Good eg GoodInt and Bad eg
 * BadInt sub classes. It can be Good eg GoodInt containing a value of A eg Int, or Bad eg BadInt containing a Refs of Strings. A special case of Bad
 * is the NoGood object eg NoInt which has not value of type A and no error strings. To avoid boxeing most methods are left abstract to be implemented
 * in the leaf classes, to avoid unnecessary boxing on generic functions. */
trait EMonBase[+A]
{
  def mMap[B, BB <: EMonBase[B]](f: A => B)(implicit build: EMonBuild[B, BB]): BB
  def errs: Strings
  /** Will perform action if Good. Does nothing if Bad. */
  def forGood(f: A => Unit): Unit
  /** Fold the EMon of type A into a type of B. */
  @inline def foldErrs[B](fGood: A => B)(fBad: Strings => B): B
  def fold[B](noneValue: => B)(fGood: A => B): B
}

trait OptBase[+A] extends EMonBase[A]

trait GoodBase[+A] extends OptBase[A]
{ def value: A
  def errs: Strings = Refs()
}

trait BadBase[+A] extends EMonBase[A]
{ override def forGood(f: A => Unit): Unit = {}
}

trait NoBase[+A] extends BadBase[A]

trait EMonInt extends EMonBase[Int]
{ def mMap[B, BB <: EMonBase[B]](f: Int => B)(implicit build: EMonBuild[B, BB]): BB
}

trait OptInt extends EMonInt

case class GoodInt(value: Int) extends OptInt with GoodBase[Int]
{ override def mMap[B, BB <: EMonBase[B]](f: Int => B)(implicit build: EMonBuild[B, BB]): BB = build(f(value))
  override def forGood(f: Int => Unit): Unit = f(value)
  override def fold[B](noneValue: => B)(fGood: Int => B): B = fGood(value)
  @inline override def foldErrs[B](fGood: Int => B)(fBad: Strings => B): B = fGood(value)
}
case class BadInt(errs: Refs[String]) extends EMonInt with BadBase[Int]
{ override def mMap[B, BB <: EMonBase[B]](f: Int => B)(implicit build: EMonBuild[B, BB]): BB = build.newBad(errs)
  override def fold[B](noneValue: => B)(fGood: Int => B): B = noneValue
  @inline override def foldErrs[B](fGood: Int => B)(fBad: Strings => B): B = fBad(errs)
}

object NoInt extends BadInt(Refs()) with OptInt with NoBase[Int]