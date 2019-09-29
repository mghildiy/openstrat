/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

trait PolyElem extends Any with PaintElem
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

/** Immutable Graphic element that defines, fills a Polygon. */ 
case class PolyFill(verts: Polygon, colour: Colour) extends PolyElem
{ override def fTrans(f: Vec2 => Vec2): PolyFill = PolyFill(verts.fTrans(f), colour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.polyFill(this)
}

/** Immutable Graphic element that defines, draws a Polygon. */
case class PolyDraw(verts: Polygon, lineWidth: Double, colour: Colour = Black) extends PolyElem
{ override def fTrans(f: Vec2 => Vec2): PolyDraw = PolyDraw(verts.fTrans(f), lineWidth, colour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.polyDraw(this)
}

case class PolyFillDraw(verts: Polygon, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PolyElem
{ override def fTrans(f: Vec2 => Vec2) = PolyFillDraw(verts.fTrans(f), fillColour, lineWidth, lineColour)
  def noFill: PolyDraw = PolyDraw(verts, lineWidth, lineColour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.polyFillDraw(this)
}

/** A pointable polygon without visual */
case class PolyActive(poly: Polygon, evObj: AnyRef) extends GraphicElem with PolyActiveTr
{ override def fTrans(f: Vec2 => Vec2): PolyActive = PolyActive(poly.fTrans(f), evObj) }

case class PolyFillText(verts: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, textColour: Colour = Black) extends PolyElem
{
  override def fTrans(f: Vec2 => Vec2) = PolyFillText(verts.fTrans(f), fillColour, str,fontSize, textColour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = { cp.polyFill(fillOnly); cp.textGraphic(textOnly) }
  def textOnly: TextGraphic = TextGraphic(str, fontSize, verts.boundingRect.cen, textColour, CenAlign)
  def fillOnly: PolyFill = PolyFill(verts, fillColour)
}

case class PolyFillDrawText(verts: Polygon, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2,
   lineColour: Colour = Black) extends PolyElem
{
  override def fTrans(f: Vec2 => Vec2) = PolyFillDrawText(verts.fTrans(f), fillColour, str,fontSize, lineWidth, lineColour)
  def drawOnly: PolyDraw = PolyDraw(verts, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, verts.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolyFillDraw = PolyFillDraw(verts, fillColour, lineWidth, lineColour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = { cp.polyFillDraw(fillDrawOnly); cp.textGraphic(textOnly) }
}
