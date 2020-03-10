package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag, scala.annotation.unused

trait ArrBuildBase[ArrT <: ArrImut[_]]
{
  /** BuffT can be inbuilt Jvm type like ArrayBuffer[Int] for B = Int and BB = Ints, or it can be a compilte time wrapped Arraybuffer inheriting from
      BuffProdHomo. */
  type BuffT
  def buffNew(length: Int = 4): BuffT
  def buffToArr(buff: BuffT): ArrT
  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: ArrT): Unit
}

/** ArrFlatBuild[BB] is a type class for the building of efficient compact Immutable Arrays through a bind method, which works similar to flatMap on
 * standard Library collections. It is called bind rather than flatMap partly to distinguish it and party so as it can be used as extension method on
 *  Standard Library collections. Instances for this typeclass for classes / traits you control should go in the companion object of BB. This is
 *  different from the related ArrBuild[BB] typeclass where the instance should go into the B companion object. */
trait ArrArrBuild[ArrT <: ArrImut[_]] extends ArrBuildBase[ArrT]

object ArrArrBuild
{
  implicit val intsImplicit = IntsBuild
  implicit val dblsImplicit = DblsBuild
  implicit val longsImplicit = LongsBuild

  implicit def refsImplicit[A <: AnyRef](implicit ct: ClassTag[A], @unused notA: Not[ProdHomo]#L[A]): ArrArrBuild[Refs[A]] = new ArrArrBuild[Refs[A]]
{ type BuffT = ArrayBuffer[A]
  //override def imutNew(length: Int): Refs[A] = new Refs(new Array[A](length))
  //override def imutSet(arr: Refs[A], index: Int, value: A): Unit = arr.array(index) = value
  override def buffNew(length: Int = 4): ArrayBuffer[A] = new ArrayBuffer[A](length)
  def buffGrow(buff: ArrayBuffer[A], value: A): Unit = buff.append(value)
  override def buffGrowArr(buff: BuffT, arr: Refs[A]): Unit = buff.addAll(arr.array)
  //arr.foreach(buffGrow(buff, _))
  override def buffToArr(buff: ArrayBuffer[A]): Refs[A] = new Refs(buff.toArray)
}
}

/** ArrBuilder[B, BB] is a type class for the building of efficient compact Immutable Arrays. Instances for this typeclass for classes / traits you
 *  control should go in the companion object of B not the companion object of not BB. This is different from the related ArrBinder[BB] typeclass
 *  where instance should go into the BB companion object. The type parameter is named B rather than A, because normally this will be found by an
 *  implicit in the context of a function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever
 *  possible they should not be used directly by end users. */
trait ArrBuild[B, ArrT <: ArrImut[B]] extends ArrBuildBase[ArrT]
{
  def imutNew(length: Int): ArrT
  def imutSet(arr: ArrT, index: Int, value: B): Unit

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, value: B): Unit

  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  def buffGrowArr(buff: BuffT, arr: ArrT): Unit// = arr.foreach(buffGrow(buff, _))

  /** A mutable operation that extends the ArrayBuffer with the elements of the Iterable operand. */
  def buffGrowIter(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffGrow(buff, _))
  
  //def buffToArr(buff: BuffT): ArrT

  def iterMap[A](inp: Iterable[A], f: A => B): ArrT =
  { val buff = buffNew()
    inp.foreach(a => buffGrow(buff, f(a)))
    buffToArr(buff)
  }
}

object ArrBuild
{
  implicit val intsImplicit = IntsBuild
  implicit val doublesImplicit = DblsBuild
  implicit val longImplicit = LongsBuild

  /** This is currently set up to exclude types not extending AnyRef. The notA implicit parameter is to exclude types that are Homogeneous value
   * types. */
  implicit def refsImplicit[A <: AnyRef](implicit ct: ClassTag[A], @unused notA: Not[ProdHomo]#L[A]): ArrBuild[A, Refs[A]] = new ArrBuild[A, Refs[A]]
  { type BuffT = ArrayBuffer[A]
    override def imutNew(length: Int): Refs[A] = new Refs(new Array[A](length))
    override def imutSet(arr: Refs[A], index: Int, value: A): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[A] = new ArrayBuffer[A](length)
    override def buffGrow(buff: ArrayBuffer[A], value: A): Unit = buff.append(value)
    override def buffGrowArr(buff: ArrayBuffer[A], arr: Refs[A]): Unit = buff.addAll(arr.array)
    override def buffToArr(buff: ArrayBuffer[A]): Refs[A] = new Refs(buff.toArray)
  }

  implicit val booleansImplicit: ArrBuild[Boolean, Booleans] = new ArrBuild[Boolean, Booleans]
  { type BuffT = ArrayBuffer[Boolean]
    override def imutNew(length: Int): Booleans = new Booleans(new Array[Boolean](length))
    override def imutSet(arr: Booleans, index: Int, value: Boolean): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[Boolean] = new ArrayBuffer[Boolean](length)
    override def buffGrow(buff: ArrayBuffer[Boolean], value: Boolean): Unit = buff.append(value)
    override def buffGrowArr(buff: ArrayBuffer[Boolean], arr: Booleans): Unit = buff.addAll(arr.array)
    override def buffToArr(buff: ArrayBuffer[Boolean]): Booleans = new Booleans(buff.toArray)
  }

  implicit val floatImplicit: ArrBuild[Float, Floats] = new ArrBuild[Float, Floats]
  { type BuffT = ArrayBuffer[Float]
    override def imutNew(length: Int): Floats = new Floats(new Array[Float](length))
    override def imutSet(arr: Floats, index: Int, value: Float): Unit = arr.array(index) = value
    override def buffNew(length: Int = 4): ArrayBuffer[Float] = new ArrayBuffer[Float](length)
    override def buffGrow(buff: ArrayBuffer[Float], value: Float): Unit = buff.append(value)
    override def buffGrowArr(buff: ArrayBuffer[Float], arr: Floats): Unit = buff.addAll(arr.array)
    override def buffToArr(buff: ArrayBuffer[Float]): Floats = new Floats(buff.toArray)
  }
}