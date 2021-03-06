/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._

/** A coordinate in a TileGrid.Not sure how useful this trait is. */
trait TCoord extends Any
{ def r: Int
  def c: Int
  def toVec2: Vec2
  def typeStr: String
  def rcStr: String = s"$r, $c"
  def parenthStr: String = rcStr.enParenth
  def str: String = typeStr + parenthStr
  override def toString: String = typeStr + parenthStr
}