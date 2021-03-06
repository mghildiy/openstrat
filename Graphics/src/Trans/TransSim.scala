/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** A Similar Transformations type class */
trait TransSim[T] extends TransAlign[T]
{ def reflectT(obj: T, line: Line): T
  def reflectSegT(obj: T, lineSeg: LineSeg): T
  def rotate(obj: T, angle: Angle): T
}

/** Companion object for the TranSim transformation type class trait. */
object TransSim
{
  implicit def transSimerImplicit[T <: SimilarPreserve]: TransSim[T] = new TransSim[T]
  { override def rotate(obj: T, angle: Angle): T = obj.rotate(angle).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def reflectSegT(obj: T, line: LineSeg): T = obj.reflect(line).asInstanceOf[T]
    override def reflectT(obj: T, line: Line): T = obj.reflect(line).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransSim[A]): TransSim[AA] = new TransSim[AA]
  { override def slate(obj: AA, offset: Vec2): AA = obj.map(ev.slate(_, offset))
    override def rotate(obj: AA, angle: Angle): AA = obj.map(ev.rotate(_, angle))
    override def reflectSegT(obj: AA, line: LineSeg): AA = obj.map(ev.reflectSegT(_, line))
    override def reflectT(obj: AA, line: Line): AA = obj.map(ev.reflectT(_, line))
    override def scale(obj: AA, operand: Double): AA = obj.map(ev.scale(_, operand))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransSim[A]): TransSim[F[A]] = new TransSim[F[A]]
  { override def slate(obj: F[A], offset: Vec2): F[A] = evF.mapT(obj, ts => evA.slate(ts, offset))
    override def rotate(obj: F[A], angle: Angle): F[A] = evF.mapT(obj, ts => evA.rotate(ts, angle))
    override def reflectSegT(obj: F[A], line: LineSeg): F[A] = evF.mapT(obj, evA.reflectSegT(_, line))
    override def reflectT(obj: F[A], line: Line): F[A] = evF.mapT(obj, evA.reflectT(_, line))
    override def scale(obj: F[A], operand: Double): F[A] = evF.mapT[A, A](obj, ts => evA.scale(ts, operand))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: TransSim[A]): TransSim[Array[A]] = new TransSim[Array[A]]
  { override def slate(obj: Array[A], offset: Vec2): Array[A] = obj.map(ev.slate(_, offset))
    override def rotate(obj: Array[A], angle: Angle): Array[A] = obj.map(ev.rotate(_, angle))
    override def reflectSegT(obj: Array[A], line: LineSeg): Array[A] = obj.map(ev.reflectSegT(_, line))
    override def reflectT(obj: Array[A], line: Line): Array[A] = obj.map(ev.reflectT(_, line))
    override def scale(obj: Array[A], operand: Double): Array[A] = obj.map(ev.scale(_, operand))
  }
}

class TransSimExtension[T](value: T, ev: TransSim[T])
{ def reflect(line: Line) = ev.reflectT(value, line)
  def reflect(lineSeg: LineSeg): T = ev.reflectSegT(value, lineSeg)
   
  /** this.asInstanceOf[T] */
  def identity: T = this.asInstanceOf[T]

  /** The scale transformation on 2 dimensional vectors. */
  def scaleSlate(factor: Double, addVec: Vec2): T =
  { val r1 = ev.scale(value, factor)
    ev.slate(r1, addVec)
  }
}