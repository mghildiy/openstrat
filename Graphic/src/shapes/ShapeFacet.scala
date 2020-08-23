/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pXml._, Colour.Black
trait ShapeFacet// extends ShapeMember
{ def attribs: Arr[Attrib]
}

case class FillColour(colour: Colour) extends ShapeFacet
{
  override def attribs: Arr[Attrib] = Arr(FillAttrib(colour))
  def fillAttrib: FillAttrib = FillAttrib(colour)
}

case class ShapeActive(id: Any) extends ShapeFacet
{ override def attribs: Arr[Attrib] = Arr()
}

trait CurveFacet extends ShapeFacet

case class CurveDraw(width: Double = 2.0, colour: Colour = Black) extends CurveFacet
{ def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(width)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(colour)
  override def attribs: Arr[Attrib] = Arr(strokeWidthAttrib, strokeAttrib)
}