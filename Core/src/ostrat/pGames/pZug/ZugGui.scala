/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import geom._
import pCanv._
import Colour._
import pGrid._
import pStrat._ 

class ZugGui(canv: CanvasPlatform) extends HexGridGui[ZugTile, ZugSide, ZugGrid](canv, Zug1)
{
   override def scaleMin = 10
   override def eTop(): Unit = reTop(guButs :+ status)
   mapPanel.backColour = Black
   def fHex: OfHexReg[ZugTile, ZugSide, ZugGrid] => Disp2 = ofh =>
      {
         import ofh._         
         val colour: Colour = tile.colour         
         val tv = tile.terr match
         {
            case _ : Building => List(vertDispVecs.fillSubj(tile, LightGreen), Square.fill(pScale * 2.5, colour, cen))
            case _ => vertDispVecs.fillSubj(tile, colour) :: Nil        
         }
         val tText = ifScaleCObj(60, TextGraphic(cen, xyStr, 14, colour.contrastBW))
         val lunit = tile.lunits match
         {
            case ::(head, _) if tScale > 68 => Some(UnitCounters.infantry(30, head, head.colour,tile.colour).slate(cen))
            case _ => None   
         }         
         Disp2(tv, tText ++ lunit)
      }
   def fSide: OfHexSideReg[ZugTile, ZugSide, ZugGrid] => Disp2 = ofs => {
      import ofs._
      val line: GraphicElems = ifScaleCObjs(60, side.wall match
         {
         case true => vertDispLine.draw(6, Gray) :: Nil
         case _ => ifTiles(_.colour == _.colour, (t1, _) => vertDispLine.draw(1, t1.colour.contrastBW))
      })
      Disp2(Nil, line)
   }
   def dSides: Disp2 = ofSidesDisplayFold(fSide)
     
   def mapObjs: GraphicElems = (ofTilesDisplayFold(fHex) ++ dSides ).collapse//ofHexsDisplayFold(fHex).collapse
     
   mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
   {
      case (LeftButton, _, cl) =>
         {
            debvar(clickList)
            selected = clickList.fHead(Nil, List(_))
            statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
            eTop()            
         }
      case (RightButton, List(squad : Squad), List(newTile: ZugTile)) =>
         {
            val newCood = newTile.cood
            val oldCood = squad.cood
            if (HexGrid.adjTileCoodsOfTile(oldCood).contains(newCood) && squad.canMove(newTile))
            {
               val oldTile = grid.getTile(oldCood)
               oldTile.lunits = oldTile.lunits.removeFirst(_ == squad)
               squad.cood = newCood
               newTile.lunits ::= squad             
               repaintMap
            }            
         }      
      case _ => 
   }   
   eTop()
   mapPanel.repaint(mapObjs)
}
  