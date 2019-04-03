/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

trait PolyElem[A] extends Any with PaintElem[A]
{
   def verts: Polygon
   def xHead: Double = verts.head1
   def yHead: Double = verts.head2
   /** The number of vertices. */
   def vertsLen: Int = verts.length
   /** Checks for 2 or more vertices */
   def ifv2: Boolean = verts.length >= 2
   /** Checks for 3 or more vertices */
   def ifv3: Boolean = verts.length >= 3
   def xArray: Array[Double] = verts.elem1sArray
   def yArray: Array[Double] = verts.elem2sArray
}

case class PolyFill(verts: Polygon, colour: Colour, zOrder: Int = 0) extends PolyElem[PolyFill]
{
  override def fTrans(f: Vec2 => Vec2): PolyFill = PolyFill(verts.fTrans(f), colour, zOrder)
  
}

object PolyFill
{
  
}

case class PolyDraw(verts: Polygon, lineWidth: Double, colour: Colour = Black, zOrder: Int = 0) extends PolyElem[PolyDraw]
{ 
  override def fTrans(f: Vec2 => Vec2): PolyDraw = PolyDraw(verts.fTrans(f), lineWidth, colour, zOrder) 
}

case class PolyFillDraw(verts: Polygon, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black, zOrder: Int = 0) extends
PolyElem[PolyFillDraw]
{
  override def fTrans(f: Vec2 => Vec2) = PolyFillDraw(verts.fTrans(f), fillColour, lineWidth, lineColour, zOrder)
  def noFill: PolyDraw = PolyDraw(verts, lineWidth, lineColour, zOrder)
}
