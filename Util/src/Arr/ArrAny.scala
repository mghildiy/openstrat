package ostrat
import annotation._, unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** The immutable Array based class for reference types. It Inherits the standard foreach, map, flatMap and fold and their variations' methods from
 *  ArrayLike. */
final class ArrAny[+A](val unsafeArr: Array[A] @uncheckedVariance) extends AnyVal with ArrBase[A]
{ type ThisT = ArrAny[A] @uncheckedVariance
  override def unsafeNew(length: Int): ArrAny[A] = new ArrAny(new Array[Any](length).asInstanceOf[Array[A]])
  override def length: Int = unsafeArr.length
  override def apply(index: Int): A = unsafeArr(index)
  override def toString: String = "Refs" + elemsStr
  def elemsStr: String =  unsafeArr.toStrsCommaParenth()
  def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit = unsafeArr(i) = value
  @inline def drop1(implicit ct: ClassTag[A] @uncheckedVariance): ArrAny[A] = drop(1)
  //def offset(value: Int): RefsOff[A] @uncheckedVariance = new RefsOff[A](value)
  //def offset0: RefsOff[A @uncheckedVariance] = offset(0)

  override def unsafeArrayCopy(operand: Array[A] @uncheckedVariance, offset: Int, copyLength: Int): Unit =
  { unsafeArr.copyToArray(unsafeArr, offset, copyLength); () }

  def drop(n: Int)(implicit ct: ClassTag[A] @uncheckedVariance): ArrAny[A] =
  { val newArray = new Array[A]((length - 1).min0)
    iUntilForeach(1, length)(i => newArray(i - 1) = unsafeArr(i))
    new ArrAny(newArray)
  }

  /** Alias for appendRefs. Functionally appends 2nd Refs collection to dispatching Refs, allows type widening. This operation allows type widening.*/
  @inline def ++ [AA >: A](op: ArrAny[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): ArrAny[AA] = appendRefs[AA](op)(ct)
  /** Functionally concatenates element to dispatching Refs, allows type widening. Aliased by -+ operator. The '-' character in the operator name
   *  indicates loss of type precision. The ++ appendRefs method is preferred when type widening is not required. */
  def appendRefs [AA >: A](op: ArrAny[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): ArrAny[AA] =
  { val newArray = new Array[AA](length + op.length)
    unsafeArr.copyToArray(newArray)
    op.unsafeArr.copyToArray(newArray, length)
    new ArrAny(newArray)
  }

  /** Alias for concat. Functionally concatenates element to dispatching Refs, allows type widening. */
  @inline def +- [AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): ArrAny[AA] = append[AA](op)(ct)
  /** Functionally appends an element to dispatching Refs, allows type widening. Aliased by +- operator. */
  def append[AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): ArrAny[AA] =
  { val newArray = new Array[AA](length + 1)
    unsafeArr.copyToArray(newArray)
    newArray(length) = op
    new ArrAny(newArray)
  }

  /** Alias for prepend. Functionally prepends element to array. Allows type widening. There is no precaternateRefs method, as this would serve no
   *  purpose. The ::: method on Lists is required for performance reasons. */
  @inline def +: [AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA] @uncheckedVariance): ArrAny[AA] = prepend(op)(ct)
  /** Functionally prepends element to array. Aliased by the +: operator. */
  def prepend[AA >: A](op: AA @uncheckedVariance)(implicit ct: ClassTag[AA]): ArrAny[AA] =
  { val newArray = new Array[AA](length + 1)
    newArray(0) = op
    unsafeArr.copyToArray(newArray, 1)
    new ArrAny(newArray)
  }

  /** Concatenates the elements of the operands Refs if the condition is true, else returns the original Refs. The return type is the super type of
   *  the original Refs and the operand Ref. The operand is lazy so will only be evaluated if the condition is true. This is similar to the appendsIf
   *  method, but concatsIf allows type widening. */
  def concatRefsIf[AA >: A](b: Boolean, newElems: => ArrAny[AA])(implicit ct: ClassTag[AA]): ArrAny[AA] =
    ife(b,this ++ newElems, this)

  /** Appends the elements of the operand Refs if the condition is true, else returns the original Refs. The operand is lazy so will only be evaluated
   *  if the condition is true. This is similar to the concatsIf method, but appendsIf does not allow type widening. */
  def appendRefsIf(b: Boolean, newElems: => ArrAny[A] @uncheckedVariance)(implicit ct: ClassTag[A] @uncheckedVariance): ArrAny[A] =
    ife(b,this ++ newElems, this)

  def concatOption[AA >: A](optElem: Option[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): ArrAny[AA] =
    optElem.fld(this, this +- _)

  def appendOption(optElem: Option[A]@uncheckedVariance)(implicit ct: ClassTag[A] @uncheckedVariance): ArrAny[A] =
    optElem.fld(this, this +- _)

  def appendsOption(optElem: Option[ArrAny[A]]@uncheckedVariance)(implicit @unused ct: ClassTag[A] @uncheckedVariance): ArrAny[A] =
    optElem.fld(this, ++ _)

  def concatsOption[AA >: A](optElems: Option[ArrAny[AA]])(implicit ct: ClassTag[AA]): ArrAny[AA] =
    optElems.fld[ArrAny[AA]](this, this ++ _)

  def setAll(value: A @uncheckedVariance): Unit =
  { var i = 0
    while(i < length){unsafeSetElem(i, value); i += 1}
  }
}

class AnysBuild[A](implicit ct: ClassTag[A]/*, @unused notA: Not[ProdHomo]#L[A]*/) extends ArrBuild[A, ArrAny[A]] with ArrFlatBuild[ArrAny[A]]
{ type BuffT = AnyBuff[A]
  override def newArr(length: Int): ArrAny[A] = new ArrAny(new Array[A](length))
  override def arrSet(arr: ArrAny[A], index: Int, value: A): Unit = arr.unsafeArr(index) = value
  override def newBuff(length: Int = 4): AnyBuff[A] = new AnyBuff(new ArrayBuffer[A](length))
  override def buffGrow(buff: AnyBuff[A], value: A): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: AnyBuff[A], arr: ArrAny[A]): Unit = buff.unsafeBuff.addAll(arr.unsafeArr)
  override def buffToArr(buff: AnyBuff[A]): ArrAny[A] = new ArrAny(buff.unsafeBuff.toArray)
}

object ArrAny
{ def apply[A](input: A*)(implicit ct: ClassTag[A]): ArrAny[A] = new ArrAny(input.toArray)
  implicit def showImplicit[A](implicit evA: Show[A]): Show[ArrAny[A]] = ArrayLikeShow[A, ArrAny[A]](evA)

  implicit class ArrAnyRefsExtension[A <: AnyRef](thisArr: ArrAny[A])
  {
    def optFind(f: A => Boolean): OptRef[A] =
    { var acc: OptRef[A] = NoRef
      var count = 0
      while (acc == NoRef & count < thisArr.length) if (f(thisArr(count))) acc = OptRef(thisArr(count)) else count += 1
      acc
    }
  }
}

class AnyBuff[A](val unsafeBuff: ArrayBuffer[A]) extends AnyVal with ArrayLike[A]
{ override def apply(index: Int): A = unsafeBuff(index)
  override def length: Int = unsafeBuff.length
}