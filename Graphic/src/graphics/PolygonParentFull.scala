/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** Polygon based Graphic class that constains a number of child Graphic Elements. */
case class PolygonParentFull(cen: Vec2, poly: Polygon, pointerId: Any, children: Arr[PaintFullElem]) extends GraphicParentFull with PolyActive
{ type ThisT = PolygonParentFull
  def fTrans(f: Vec2 => Vec2): PolygonParentFull = new PolygonParentFull(f(cen), poly.fTrans(f), pointerId, children.trans(f))
  override def addElems(newElems: Arr[PaintFullElem]): PolygonParentFull = new PolygonParentFull(cen, poly, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolygonParentFull = new PolygonParentFull(cen, poly, newObj, children)
}

/** Companion object of the PolygonParent case class. */
object PolygonParentFull
{
  def fill(cen: Vec2, poly: Polygon, evObj: Any, colour: Colour): PolygonParentFull = new PolygonParentFull(cen, poly, evObj, Arr(poly.fill(colour)))

  /** Not sure if this is double filling the polygon */
  def fillDraw(cen: Vec2, poly: Polygon, evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): PolygonParentFull =
    new PolygonParentFull(cen, poly, evObj, Arr(PolygonFillDraw(poly, fillColour, lineWidth, lineColour)))

  def draw(cen: Vec2, poly: Polygon, evObj: Any, lineWidth: Double, lineColour: Colour = Black): PolygonParentFull =
    new PolygonParentFull(cen, poly, evObj, Arr(PolygonDraw(poly, lineWidth, lineColour)))

  def fillText(cen: Vec2, poly: Polygon, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4, fontColour: Colour = Colour.Black,
    align: TextAlign = CenAlign): PolygonParentFull =
    new PolygonParentFull(cen, poly, evObj, Arr(poly.fill(fillColour), TextGraphic(str, fontSize, poly.polyCentre, fontColour, align)))

  def fillContrastText(cen: Vec2, poly: Polygon, evObj: Any, fillColour: Colour, str: String, fontSize: Int = 4): PolygonParentFull =
    fillText(cen, poly, evObj, fillColour, str, fontSize, fillColour.contrast)
}

/** Polygon based Graphic class that constains a number of child Graphic Elements. */
case class PolygonParent(cen: Vec2, poly: Polygon, pointerId: Any, children: Arr[PaintElem]) extends GraphicParent //with PolyActive
{ type ThisT = PolygonParent
  //def fTrans(f: Vec2 => Vec2): PolygonParentFull = new PolygonParentFull(f(cen), poly.fTrans(f), pointerId, children.trans(f))
  override def addElems(newElems: Arr[PaintElem]): PolygonParent = new PolygonParent(cen, poly, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolygonParent = new PolygonParent(cen, poly, newObj, children)

  def mirrorXOffset(yOffset: Double): PolygonParent = ???
   // PolygonParent(cen.mirrorXOffset(yOffset), poly.mirrorXOffset(yOffset), pointerId, children.mirrorXOffset(yOffset))

  def mirrorYOffset(xOffset: Double): PolygonParent = ???
  def rotateRadians(radians: Double): PolygonParent = ???
  def slate(offset: Vec2): PolygonParent = ???
  def boundingRect: BoundingRect = ???
  def scale(operand: Double): PolygonParent = ???
}