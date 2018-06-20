/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
import Colour.Black
import pDisp._

case class PolySubj(cen: Vec2, poly: Vec2s, evObj: AnyRef, elems: Seq[CanvEl[_]]) extends CanvSubj[PolySubj] with ClickPolyTr
{  
   def fTrans(f: Vec2 => Vec2): PolySubj = new PolySubj(f(cen), poly.fTrans(f), evObj, elems.fTrans(f))   
   override def addElems(newElems: Seq[CanvEl[_]]): PolySubj = new PolySubj(cen, poly, evObj, elems ++ newElems)
   override def mutObj(newObj: AnyRef): PolySubj = new PolySubj(cen, poly, newObj, elems)
}

object PolySubj
{
   def fill(cen: Vec2, poly: Vec2s, evObj: AnyRef, colour: Colour) = new PolySubj(cen, poly, evObj, Seq(FillPoly(poly, colour)))
   
   /** Not sure if this is double filling the polygon */
   def fillDraw(cen: Vec2, poly: Vec2s, evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) =
      new PolySubj(cen, poly, evObj, Seq(FillDrawPoly(poly, fillColour, lineWidth, lineColour)))
   
   def draw(cen: Vec2, poly: Vec2s, evObj: AnyRef, lineWidth: Double, lineColour: Colour = Black) =
      new PolySubj(cen, poly, evObj, Seq(DrawPoly(poly, lineWidth, lineColour)))
   def fillText(cen: Vec2, poly: Vec2s, evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 4,
         fontColour: Colour = Colour.Black, align: TextAlign = TextCen) =
      new PolySubj(cen, poly, evObj, Seq(FillPoly(poly, fillColour), FillText(poly.polyCentre, str, fontSize, fontColour, align)))
   def fillContrastText(cen: Vec2, poly: Vec2s, evObj: AnyRef, fillColour: Colour, str: String, fontSize: Int = 4) =
      fillText(cen, poly, evObj, fillColour, str, fontSize, fillColour.contrast)
}

