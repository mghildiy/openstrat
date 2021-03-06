/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A simple plain colour fill graphic. */
trait ShapeFill extends ShapeGraphicSimple
{ /** The colour of this fill graphic. */
  def colour: Colour

  /** The fill attribute for SVG. */
  def fillAttrib: FillAttrib = FillAttrib(colour)
  override def nonShapeAttribs: Arr[XmlAtt] = Arr(fillAttrib)

  def toDraw(lineWidth: Double = 2, newColour: Colour = colour): ShapeDraw

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): ShapeFill

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): ShapeFill

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeFill

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: ShapeFill

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: ShapeFill


  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a ShapeGraphic, returns a ShapeGraphic. The
   * return type will be narrowed in sub traits / classes. */
  override def rotate90: ShapeFill

  /** Rotate 180 degrees 2D geometric transformation on a ShapeGraphic, returns a ShapeGraphic. The return type will be narrowed in sub traits /
   * classes. */
  override def rotate180: ShapeFill

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a ShapeGraphic, returns a ShapeGraphic. The
   * return type will be narrowed in sub traits / classes. */
  override def rotate270: ShapeFill

  override def prolign(matrix: ProlignMatrix): ShapeFill

  override def rotate(angle: Angle): ShapeFill

  override def reflect(lineLike: LineLike): ShapeFill

  override def xyScale(xOperand: Double, yOperand: Double): ShapeFill  

  //override def reflect(line: LineSeg): ShapeFill

  override def xShear(operand: Double): ShapeFill

  override def yShear(operand: Double): ShapeFill  
}

object ShapeFill
{
  implicit val slateImplicit: Slate[ShapeFill] = (obj: ShapeFill, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[ShapeFill] = (obj: ShapeFill, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeFill] = (obj: ShapeFill, angle : Angle) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[ShapeFill] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[ShapeFill] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[ShapeFill] = new TransAxes[ShapeFill]
  { override def negYT(obj: ShapeFill): ShapeFill = obj.negY
    override def negXT(obj: ShapeFill): ShapeFill = obj.negX
    override def rotate90T(obj: ShapeFill): ShapeFill = obj.rotate90
    override def rotate180T(obj: ShapeFill): ShapeFill = obj.rotate180
    override def rotate270T(obj: ShapeFill): ShapeFill = obj.rotate270
  }
}