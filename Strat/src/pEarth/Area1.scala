/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._

abstract class Area1(val symName: String, val cen: LatLong) extends GeographicSymbolKey
{   
  def neighbs: Refs[Area1] = Refs()
  def a2Seq: Refs[Area2]
  def fill: Boolean = true
  //def a2r(eg: EarthGui): GraphicElems = a2Seq.mdisplayFold(_.display(eg, fill))
  def disp2(eg: EarthGui): GraphicElems = a2Seq.flatMap(_.display(eg, fill))//.toArraySeq
}
