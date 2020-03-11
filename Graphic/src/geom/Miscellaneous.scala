package ostrat
package geom

object Star5
{
  val classicRatio = 0.38

  /** The ratio for the standard star is approx 0.4. */
  def apply(ratio: Double = classicRatio): Polygon =
  { val l2: Vec2s = Vec2s(Vec2(0, 1), Vec2(0, ratio).rotate(-deg36))
    iToFlatMap(0, 4)(i => l2.rotate(-deg72 * i)).toPolygon
  }

  def fill(colour: Colour, ratio: Double = classicRatio): PolyFill = apply(ratio).fill(colour)

  def crossLines(ratio: Double = classicRatio) =
  {
    val poly = apply(ratio)
    Ints(0, 4, 8, 2, 6, 0).map{i => poly(i)}.toPathDraw(1)
  }

  /** Not quite sure what this does. */
  def ptUpYCentred(ratio: Double = classicRatio): Polygon = apply(ratio).slateY(deg36.cos / 2 - 0.5)
}