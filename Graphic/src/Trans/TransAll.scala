/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import reflect.ClassTag

/** An object that can transform itself in 2d geometry. This is a key trait, the object can be transformed in 2 dimensional space. Leaf classes must
 *  implement the single method fTrans(f: Vec2 => Vec2): T. The related trait TransDistable  does the same for fTrans(f: Dist2 => Dist2):  T.  */
trait TranserAll extends Any with TransAffer
{ type ThisT <: TranserAll
  def fTrans(f: Vec2 => Vec2): ThisT
  def slate(offset: Vec2): ThisT = fTrans(_ + offset)
  def scale(operand: Double): ThisT = fTrans(_ * operand)
  def shear(xScale: Double, yScale: Double): ThisT = fTrans{case Vec2(x, y) => x * xScale vv y * yScale}
  def mirrorXOffset(yOffset: Double): ThisT = fTrans(_.mirrorXOffset(yOffset))
  def mirrorYOffset(xOffset: Double): ThisT = fTrans(_.mirrorYOffset(xOffset))
  def rotateRadians(radians: Double): ThisT = fTrans(_.rotateRadians(radians))
}

/** The typeclass trait for transforming an object in 2d geometry. */
trait TransAll[T] extends TransAff[T]
{ def trans(obj: T, f: Vec2 => Vec2):  T
  override def slate(obj: T, offset: Vec2): T = trans(obj, _ + offset)
  override def scale(obj: T, operand: Double): T = trans(obj, _ * operand)
  override def shear(obj: T, xScale: Double, yScale: Double): T = trans(obj, v => Vec2(v.x * xScale, v.y * yScale))
  override def rotateRadians(obj: T, radians: Double): T = trans(obj, _.rotateRadians(radians))
  def mirrorYOffset(obj: T, xOffset: Double): T = trans(obj, _.mirrorYOffset(xOffset))
  def mirrorXOffset(obj: T, yOffset: Double): T = trans(obj, _.mirrorXOffset(yOffset))
}

/** The companion object for the Trans[T] typeclass, containing instances for common classes. */
object TransAll
{
  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransAll[A]): TransAll[AA] =
    (obj, f) => obj.map(el => ev.trans(el, f))

  implicit def fromScaledImplicit[T <: TranserAll]: TransAll[T] =
    (obj, f) => obj.fTrans(f).asInstanceOf[T]

  /*implicit def fromUnScaledImplicit[T <: UnScaled]: TransAll[T#ThisT] =
    (obj, f) => obj.fTrans(f).asInstanceOf[T#ThisT]
*/
  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransAll[A]): TransAll[F[A]] =
    (obj, f) => evF.map(obj, el => evA.trans(el, f))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransAll[A]): TransAll[Array[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
}