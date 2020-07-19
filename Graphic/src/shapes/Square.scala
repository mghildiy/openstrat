/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** The class for a generalised square. If you want a square aligned XY axes use [[Sqlign]]. The square can be translated, scaled, reflected and
 *  rotated while remaining a Square. */
final class Square private(val x0: Double, val y0: Double, val x1: Double, val y1: Double) extends SquareTr with SimilarPreserve
{ override type ThisT = Square
  override def v0: Vec2 = x0 vv y0
  override def v1: Vec2 = x1 vv y1
  override def width: Double = v0.distTo(v1)
  override def cen: Vec2 = sline0.midPtToRight(width / 2)
  override def xCen: Double = cen.x
  override def yCen: Double = cen.y

  @inline def v2: Vec2 = sline0.endToRight(width)
  @inline def x2: Double = v2.x
  @inline def y2: Double = v2.y
  @inline def v3: Vec2 = sline0.startToRight(width)
  @inline def x3: Double = v3.x
  @inline def y3: Double = v3.y
  def rotationRadians: Double = rotation.radians
  @inline override def rotation: Angle =  sline0.angle + 90.degs // Angle.radians(rotationRadians)

  override def productArity: Int = 3
  override def productElement(n: Int): Any = 4
  override def toString: String = s"SquareClass($x0, $y0; $x1, $y1)"
  override def fTrans(f: Vec2 => Vec2): Square = Square.v0v1(f(v0), f(v1))

  override def slate(offset: Vec2): Square = Square(width, cen + offset)

  /** Translate geometric transformation. */
  @inline override def slate(xOffset: Double, yOffset: Double): Square = Square(width, xCen + xOffset, yCen + yOffset, rotation)

  override def scale(operand: Double): Square = Square(width * operand, cen * operand)

  override def mirrorXOffset(yOffset: Double): Square = Square(width, cen.mirrorXOffset(yOffset))

  override def mirrorX: Square = Square(width, xCen, -yCen, rotation)

  override def mirrorYOffset(xOffset: Double): Square = Square(width, cen.mirrorYOffset(xOffset))

  override def mirrorY: Square = Square.v0v1(v1.mirrorY, v0.mirrorY)

  override def prolign(matrix: ProlignMatrix): Square = Square(width * matrix.vFactor, cen.prolign(matrix), rotation)

  /* Rotates 90 degrees rotate-clockwise or + Pi/2
  override def rotate90: SquareClass = SquareClass(width, cen.rotate90)

  override def rotate180: SquareClass = SquareClass(width, cen.rotate180)

  override def rotate270: SquareClass = SquareClass(width, cen.rotate270)

  override def rotateRadians(radians: Double): SquareClass = ???
*/
  override def reflect(line: Line): Square = ???
  override def reflect(line: Sline): Square = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???

  override def fill(fillColour: Colour): ShapeFill = ???

  override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

  override def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???
}

/** Factory object for squares. */
object Square extends ShapeIcon
{
  /*@inline def apply(width: Double, cen: Vec2 = Vec2Z, rotation: Angle): Square = Square(width, cen.x, cen.y, rotation)

  @inline def apply(width: Double, xCen: Double, yCen: Double, rotation: Angle): Square = Square(width, xCen, yCen, rotation)*/

  @inline def apply(width: Double, cen: Vec2 = Vec2Z, rotation: Angle = 0.degs): Square =
  { val v0 = cen + Vec2(width / 2, width / 2).rotate(rotation)
    val v1 = cen + Vec2(width / 2, -width / 2).rotate(rotation)
    new Square(v0.x, v0.y, v1.x, v1.y)
  }

  @inline def apply(width: Double, xCen: Double, yCen: Double, rotation: Angle): Square = apply(width, xCen vv yCen, rotation)

  def v0v1(v0: Vec2, v1: Vec2): Square = new Square(v0.x, v0.y, v1.x, v1.y)

  def xy(width: Double, xCen: Double, yCen: Double): Polygon = Polygon(
      xCen - width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen - width / 2,
      xCen - width / 2 vv yCen - width / 2)

  override def scaleSlate(scale: Double, cen: Vec2): Shape = ???

  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): Shape = ???

  override def fill(colour: Colour): ShapeGraphicIcon = ???
}