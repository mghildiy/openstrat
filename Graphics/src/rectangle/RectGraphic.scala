/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This is a graphic based on a Rect shape. A rectangle aligned to the X and Y axes. */
trait RectGraphic extends RectangleGraphic with ShapeGraphicAligned
{ override def shape: Rect
}

trait RectGraphicSimple extends RectGraphic with RectangleGraphicSimple

/** A rectangular Graphic aligned to the axes, filled with a single colour. */
trait RectFill extends RectGraphicSimple with RectangleFill

/** Companion object for the RectFill trait, contains a RectFillImp implementation class and an apply method that delegates to it. */
object RectFill
{
  /** Factory method for RectFill. A rectangular Graphic aligned to the axes, filled with a single colour. it delegates to the RectfllImp i
   * implementation class, but has a return type of RectFill. */
  def apply(rect: Rect, fillColour: Colour): RectFill = RectFillImp(rect, fillColour)

  case class RectFillImp(shape: Rect, colour: Colour) extends RectFill
}