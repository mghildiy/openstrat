package ostrat
import scala.collection.mutable.ArrayBuffer

/** Not sure if this trait can be useful. */
trait ArrValues[A] extends Any with ArrImut[A]
{ type ThisT <: ArrValues[A]
  //def append(op: A): ThisT
 // { val newArr = buildThis(length + 1)
    //array.copyToArray(newArr.arr)
    // newArray(length) = op
 //   ??? //new Refs(newArray)
 // }
}

class Longs(val array: Array[Long]) extends AnyVal with ArrImut[Long]
{ type ThisT = Longs
  override def unsafeNew(length: Int): Longs = new Longs(new Array[Long](length))
  override def length: Int = array.length
  override def apply(index: Int): Long = array(index)
  override def unsafeSetElem(i: Int, value: Long): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Long], offset: Int, copyLength: Int): Unit = { array.copyToArray(array, offset, copyLength); () }

  def ++ (op: Longs): Longs =
  { val newArray = new Array[Long](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Longs(newArray)
  }
}
object Longs
{ def apply(input: Long*): Longs = new Longs(input.toArray)
  implicit val bindImplicit: ArrFlatBuild[Longs] = new ArrFlatBuild[Longs]
  {
    override def flatMap[A](orig: ArrayLike[A], f: A => Longs): Longs =
    { val buff = new ArrayBuffer[Long]
      orig.foreach(a => buff.addAll(f(a).array))
      new Longs(buff.toArray)
    }
  }
}

class Dbls(val array: Array[Double]) extends AnyVal with ArrImut[Double]
{ type ThisT = Dbls
  override def unsafeNew(length: Int): Dbls = new Dbls(new Array[Double](length))
  override def length: Int = array.length
  override def apply(index: Int): Double = array(index)
  override def unsafeSetElem(i: Int, value: Double): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Double], offset: Int, copyLength: Int): Unit = { array.copyToArray(array, offset, copyLength); () }

  def ++ (op: Dbls): Dbls =
  { val newArray = new Array[Double](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Dbls(newArray)
  }
}

object Dbls
{ def apply(input: Double*): Dbls = new Dbls(input.toArray)
  implicit val bindImplicit: ArrFlatBuild[Dbls] = new ArrFlatBuild[Dbls]
  {
    override def flatMap[A](orig: ArrayLike[A], f: A => Dbls): Dbls =
    { val buff = new ArrayBuffer[Double]
      orig.foreach(a => buff.addAll(f(a).array))
      new Dbls(buff.toArray)
    }
  }
}

class Booleans(val array: Array[Boolean]) extends AnyVal with ArrImut[Boolean]
{ type ThisT = Booleans
  override def unsafeNew(length: Int): Booleans = new Booleans(new Array[Boolean](length))
  override def length: Int = array.length
  override def apply(index: Int): Boolean = array(index)
  override def unsafeSetElem(i: Int, value: Boolean): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Boolean], offset: Int, copyLength: Int): Unit = { array.copyToArray(array, offset, copyLength); () }

  def ++ (op: Booleans): Booleans =
  { val newArray = new Array[Boolean](length + op.length)
  array.copyToArray(newArray)
  op.array.copyToArray(newArray, length)
  new Booleans(newArray)
  }
}

object Booleans
{ def apply(input: Boolean*): Booleans = new Booleans(input.toArray)
  implicit val bindImplicit: ArrFlatBuild[Booleans] = new ArrFlatBuild[Booleans]
  {
    override def flatMap[A](orig: ArrayLike[A], f: A => Booleans): Booleans =
    { val buff = new ArrayBuffer[Boolean]
      orig.foreach(a => buff.addAll(f(a).array))
      new Booleans(buff.toArray)
    }
  }
}

class Floats(val array: Array[Float]) extends AnyVal with ArrImut[Float]
{ type ThisT = Floats
  override def unsafeNew(length: Int): Floats = new Floats(new Array[Float](length))
  override def length: Int = array.length
  override def apply(index: Int): Float = array(index)
  override def unsafeSetElem(i: Int, value: Float): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Float], offset: Int, copyLength: Int): Unit = { array.copyToArray(array, offset, copyLength); () }

  def ++ (op: Floats): Floats =
  { val newArray = new Array[Float](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Floats(newArray)
  }
}

object Floats
{ def apply(input: Float*): Floats = new Floats(input.toArray)
  implicit val bindImplicit: ArrFlatBuild[Floats] = new ArrFlatBuild[Floats]
  {
    override def flatMap[A](orig: ArrayLike[A], f: A => Floats): Floats =
    { val buff = new ArrayBuffer[Float]
      orig.foreach(a => buff.addAll(f(a).array))
      new Floats(buff.toArray)
    }
  }
}