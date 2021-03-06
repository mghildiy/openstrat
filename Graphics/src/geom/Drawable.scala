/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

/** A 2D geometric elememnt that can be drawn producing a [[GraphicElem]] */
trait Drawable extends GeomElem
{
  /** Draws this geometric element to produce a [[GraphElem]] graphical element, tht can be displayed or printed.  */
  def draw(lineWidth: Double = 2, lineColour: Colour = Black): GraphicElem
}

/** A 2D geometric element that can be drawn and filled producing [[GraphicElem]]s. */
trait Fillable extends Drawable
{
  def fill(fillColour: Colour): GraphicElem
  def fillHex(intValue: Int): GraphicElem
  def fillDraw(fillColour: Colour, lineWidth: Double = 2, lineColour: Colour = Colour.Black): GraphicElem
}