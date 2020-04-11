/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug
import pGrid._

case class Squad(val polity: Polity)
{ def colour = polity.colour
}

trait Polity extends PersistSingleton
{ def colour: Colour
}

object Germany extends Polity
{ def str: String = "Germany"
  def colour = Colour.fromInts(128, 177, 179)//CadetBlue 60% shade
}

object Britain extends Polity
{ def str: String = "Britain"
  def colour = Colour.fromInts(255, 232, 184)
}

object France extends Polity
{ def str: String = "France"
  def colour = Colour.fromInts(125, 255, 255)
}

sealed trait Action
case class Move(roords: Roords) extends Action
case class Fire(roord: Roord) extends Action
object NoAction extends Action