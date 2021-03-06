/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait BoundedElem extends GeomElem
{ /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect

  /** The width of the [[BoundingRect]] of this object. */
  def boundingWidth: Double = boundingRect.width

  def boundingHeight: Double = boundingRect.height
  def boundingTR: Vec2 = boundingRect.topRight
  def boundingBR: Vec2 = boundingRect.bottomRight
  def boundingTL: Vec2 = boundingRect.topLeft
  def boundingBL: Vec2 = boundingRect.bottomLeft
  def xCen: Double
  def yCen: Double
  def cen: Vec2

  def slateTo(newCen: Vec2): BoundedElem
}

/** Type class for performing a 2D translation on an object of type T that moves the centre of the new object to the given position. */
trait SlateTo[T]
{
  /** Translate an object of type T such that the centre of the new object is given by the new position. */
  def slateTTo(obj: T, newCen: Vec2): T
}

class SlateToExtensions[A](thisA: A, ev: SlateTo[A])
{
  def slateTo(newCen: Vec2): A = ev.slateTTo(thisA, newCen)
}