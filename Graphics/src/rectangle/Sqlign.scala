/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A square aligned to the X and Y axes. */
final case class Sqlign private(width: Double, xCen: Double, yCen: Double) extends Square with Rect
{ type ThisT = Sqlign
  override def attribs: Arr[XANumeric] = ???

  override def slate(offset: Vec2): Sqlign = Sqlign(width, cen + offset)

  override def slate(xOffset: Double, yOffset: Double): Sqlign = Sqlign(width, xCen + xOffset, yCen + yOffset)
  override def scale(operand: Double): Sqlign = Sqlign(width * operand, cen * operand)

  override def negY: Sqlign = Sqlign(width, xCen, -yCen)

  override def negX: Sqlign = Sqlign(width, -xCen, yCen)

  override def prolign(matrix: ProlignMatrix): Sqlign = Sqlign(width * matrix.vFactor, cen.prolign(matrix))
  
  //override def fill(fillColour: Colour): ShapeFill = ???

 // override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
}

/** Factory object for Sqalign class. A square aligned to the X and Y axes. */
object Sqlign
{ def apply(width: Double, cen: Vec2 = Vec2Z): Sqlign = new Sqlign(width, cen.x, cen.y)
  def apply(width: Double, xCen: Double, yCen: Double): Sqlign = new Sqlign(width, xCen, yCen)
}