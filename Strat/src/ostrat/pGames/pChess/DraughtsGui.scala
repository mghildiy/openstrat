/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pChess
import pCanv._, Colour._

case class DraughtsGui(canv: CanvasPlatform) extends CanvasSimple("Draughts")
{
  var player = true

  val darkSquareColour = Brown
  val lightSquareColour = Pink  
  
//  val tiles: Seq[CheckersSq] = for { y <- 1 to rowSize; x <- 1 to rowSize } yield Cood(x, y) match
//  { case c @ Cood(x, y) if c.evenSum & y <= 3 => DarkSq(x, y, Some(BlackPiece))
//    case c @ Cood(x, y) if c.evenSum & y >= 6 => DarkSq(x, y, Some(WhitePiece))
//    case c @ Cood(x, y) if c.evenSum          => DarkSq(x, y, None)
//    case c @ Cood(x, y)                           => LightSq(x, y)          
//  }  
  
  val grid = DGrid.start
  
  val margin = 15
  val tileWidth = ((height.min(width) - margin * 2).max(100) / grid.rowSize)
  
  val stuff = grid.squares(tileWidth)// ::: grid. 
  
//  val stuff = DGrid.start.flatMap
//  {
//    case DarkSq(x, y, Some(p))  => List(
//        Square.fill(tileWidth, darkSquareColour, tileWidth * x.adj, tileWidth * y.adj),
//        Circle.fillSubj(tileWidth / 1.6, p, p.colour, tileWidth * x.adj, tileWidth * y.adj)) 
//    case DarkSq(x, y, _)  => Square.fill(tileWidth, darkSquareColour, tileWidth * x.adj, tileWidth * y.adj) :: Nil      
//    case LightSq(x, y) => Square.fill(tileWidth, lightSquareColour, tileWidth * x.adj, tileWidth * y.adj) :: Nil          
//  }
  
  repaint(stuff.toList)   
  
  mouseUp = (v, but: MouseButton, clickList) => (v, but, clickList) match
  {
    case (v, LeftButton, cl) =>
    {
      deb(cl.toString)
      //selected = clickList.fHead(Nil, (h , _) => List(h))
      //statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
      //eTop()
    }
    case _ => deb("Mouse other")
  }    
}
