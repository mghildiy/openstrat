/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import collection.mutable.ArrayBuffer

/** Compact Imutable Array[Double] based collection class for (Line2)s. */
class Line2s(val arrayUnsafe: Array[Double]) extends ArrProdDbl4[LineSeg] with AffinePreserve
{ type ThisT = Line2s
  //type ThisT = Line2s
  def unsafeFromArray(array: Array[Double]): Line2s = new Line2s(array)
  override def typeStr: String = "Line2s"
  //override def toString: String = Line2s.PersistImplict.show(this)
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSeg = new LineSeg(d1, d2, d3, d4)
  override def fTrans(f: Vec2 => Vec2): Line2s = pMap(orig => LineSeg(f(orig.pStart), f(orig.pEnd)))

  override def canEqual(that: Any): Boolean = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???
  
  def ptInPolygon(pt: Vec2): Boolean =
  { val num = foldLeft(0)((acc, line) => acc + ife(line.rayIntersection(pt), 1, 0))
    num.isOdd
  }

  def draw(lineWidth: Double, colour: Colour = Colour.Black): LinesDraw = LinesDraw(this, lineWidth, colour)
}

/** Companion object for the Line2s class. */
object Line2s extends ProdDbl4sCompanion[LineSeg, Line2s]
{
  implicit val factory: Int => Line2s = i => new Line2s(new Array[Double](i * 4))
   
  implicit val persistImplicit: ArrProdDbl4Persist[LineSeg, Line2s] = new ArrProdDbl4Persist[LineSeg, Line2s]("Line2s")
  { override def fromArray(value: Array[Double]): Line2s = new Line2s(value)
  }

  implicit val arrArrBuildImplicit: ArrFlatBuild[Line2s] = LineSeg.line2sBuildImplicit

  implicit val transImplicit: TransAff[Line2s] = (obj, f) => obj.map(_.fTrans(f))
}

/** Efficient expandable buffer for Line2s. */
class Line2sBuff(val buffer: ArrayBuffer[Double]) extends AnyVal with BuffProdDbl4[LineSeg]
{ override def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): LineSeg = new LineSeg(d1, d2, d3, d4)
}