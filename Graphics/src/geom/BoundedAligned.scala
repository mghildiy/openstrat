/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait BoundedAligned extends BoundedElem with AlignedElem

class BoundedAlignedExtensions[A <: BoundedAligned](thisA: A, ev: SlateTo[A])
{
  /** Translate an object of type T such that the top right of the new object is given by the new position. This method translates the object to an
  * absolute position. */
  def trSlateTo(newTopRight: Vec2): A = ev.slateTTo(thisA, newTopRight - thisA.topRightDelta)

  /** Translate an object of type T such that the bottom right of the new object is given by the new position. This method translates the object to an
  * absolute position. */
  def brSlateTo(newBottomRight: Vec2): A = ev.slateTTo(thisA, newBottomRight - thisA.bottomRightDelta)

  /** Translate an object of type T such that the bottom left of the new object is given by the new position. This method translates the object to an
  * absolute position. */
  def blSlateTo(newBottomLeft: Vec2): A = ev.slateTTo(thisA, newBottomLeft - thisA.bottomLeftDelta)

  /** Translate an object of type T such that the top left of the new object is given by the new position. This method translates the object to an
   * absolute position. */
  def tlSlateTo(newTopLeft: Vec2): A = ev.slateTTo(thisA, newTopLeft - thisA.topLeftDelta)


}

trait ShapeAligned extends Shape with BoundedAligned
