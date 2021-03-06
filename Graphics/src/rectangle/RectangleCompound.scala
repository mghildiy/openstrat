/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

trait RectangleCompound extends PolygonCompound with RectangleGraphic
{
  override def attribs: Arr[XmlAtt] = ???

  override def svgStr: String = ???

  override def svgElem(bounds: BoundingRect): SvgRect = SvgRect(shape.negY.slate(0, bounds.minY + bounds.maxY).
    attribs ++ facets.flatMap(_.attribs))

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): RectangleCompound = RectangleCompound(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): RectangleCompound =
    RectangleCompound(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): RectangleCompound = RectangleCompound(shape.scale(operand), facets, children.scale(operand))
  
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: RectangleCompound = RectangleCompound(shape.negY, facets, children.negY)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: RectangleCompound = RectangleCompound(shape.negX, facets, children.negX)

  override def prolign(matrix: ProlignMatrix): RectangleCompound = RectangleCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def rotate(angle: Angle): RectangleCompound = RectangleCompound(shape.rotate(angle), facets, children.rotate(angle))

  override def reflect(lineLike: LineLike): RectangleCompound = ???

  override def xyScale(xOperand: Double, yOperand: Double): RectangleCompound = ???

  override def slateTo(newCen: Vec2): PolygonCompound = ???
}

object RectangleCompound
{
  def apply(shape: Rectangle, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) : RectangleCompound =
    new RectangleCompoundImp(shape, facets, children)

  case class RectangleCompoundImp(shape: Rectangle, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) extends RectangleCompound
  {
    override def attribs: Arr[XmlAtt] = ???

    override def svgStr: String = ???

    override def svgElem(bounds: BoundingRect): SvgRect = SvgRect(shape.negY.slate(0, bounds.minY + bounds.maxY).
      attribs ++ facets.flatMap(_.attribs))

    /** Translate geometric transformation. */
    override def slate(offset: Vec2): RectangleCompoundImp = RectangleCompoundImp(shape.slate(offset), facets, children.slate(offset))

    /** Translate geometric transformation. */
    override def slate(xOffset: Double, yOffset: Double): RectangleCompoundImp =
      RectangleCompoundImp(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

    /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
     * Squares. Use the xyScale method for differential scaling. */
    override def scale(operand: Double): RectangleCompoundImp = RectangleCompoundImp(shape.scale(operand), facets, children.scale(operand))

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def negY: RectangleCompoundImp = RectangleCompoundImp(shape.negY, facets, children.negY)

    /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
     * in sub classes. */
    override def negX: RectangleCompoundImp = RectangleCompoundImp(shape.negX, facets, children.negX)

    override def prolign(matrix: ProlignMatrix): RectangleCompoundImp = RectangleCompoundImp(shape.prolign(matrix), facets, children.prolign(matrix))

    override def rotate(angle: Angle): RectangleCompoundImp = RectangleCompoundImp(shape.rotate(angle), facets, children.rotate(angle))

    override def reflect(lineLike: LineLike): RectangleCompoundImp = ???

    override def xyScale(xOperand: Double, yOperand: Double): RectangleCompoundImp = ???

    override def xShear(operand: Double): PolygonCompound = ???

    override def yShear(operand: Double): PolygonCompound = ???
  }
}