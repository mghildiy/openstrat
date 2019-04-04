/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import pGrid._ 

/** A Player has a very simple token with a letter and colour for recognition." */
class Player(val char: Char, val colour: Colour) extends WithColour
{
  override def toString = "Player " + char
}
class PossiblePlayer(val char: Char, val colour: Colour)

case class UTile(x: Int, y: Int, oPlayer: Option[Player]) extends Tile

object UTile
{
  implicit def make: (Int, Int, Option[Player]) => UTile = UTile.apply
  implicit object SimpTileIsType extends IsType[UTile]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[UTile]
    override def asType(obj: AnyRef): UTile = obj.asInstanceOf[UTile]   
  }
}

case class Move(start: Cood, dirn: HexDirn, player: Player)
{
  def destination: Cood = start + dirn.relCood
}

class UnusGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGridReg[UTile, SideBare](xTileMin, xTileMax,
    yTileMin, yTileMax)
{
  def resolveTurn(moves: List[Move]): UnusGrid =
  {
    val newGrid = new UnusGrid(xTileMin, xTileMax, yTileMin, yTileMax)
    newGrid.foreachTileAll(tile => tile.oPlayer.foreach(player => moves.find(_.player == player) match
      {
        case None => newGrid.fSetTile(tile.cood, Some(player))
     
        case Some(myMove) if moves.exists(m => player != m.player & (myMove.destination == m.start | myMove.destination == m.destination)) => 
          newGrid.fSetTile(tile.cood, Some(player))
      
        case Some(myMove) => newGrid.fSetTile(myMove.destination, Some(player))  
      }))
    newGrid
  }
}

