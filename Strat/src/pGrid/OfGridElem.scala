/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._, reflect.ClassTag

/** Although including the cood could be considered to break the principle of encapsulation, A tile should not need to know where it is in a grid. I
 *   think it is necessary. Although the cood is determined by its position in the array there is just no good way for this data to be recovered by
 *    the Grid for random access. I think also it might be better to change to a var */
trait OfGridElem[TileT <: Tile, SideT <: TileSide, GridT <: TileGrid[TileT, SideT]]
{ def grid: GridT
  def cood: Cood   
  def coodToDispVec2(inp: Cood): Vec2
  def xyStr = cood.xyStr
  def yxStr = cood.yxStr
  /** The number of pixels grid unit. The number of pixels per X Cood. Called pSc so it doesn't class with pScale from another class when the OfTile
  *   object's members are imported */
  def psc: Double
  /** The number of pixels per tile, centre to centre */
  def tScale: Double = psc * grid.xStep
  def ifScaleCObjs(ifScale: Double, cObjs: => GraphicElemsOld): GraphicElemsOld = if (tScale > ifScale) cObjs else ArrOld()
  def ifScaleCObj(ifScale: Double, cObj: CanvO *): GraphicElemsOld = if (tScale > ifScale) cObj.toArr else ArrOld()
  def ifScaleIfCObjs(ifScale: Double, b: Boolean, cObjs: => GraphicElemsOld): GraphicElemsOld = if (tScale > ifScale && b) cObjs else ArrOld()
  def ifScaleIfCObj(ifScale: Double, b: Boolean, cObjs: CanvO *): GraphicElemsOld = if (tScale > ifScale && b) cObjs.toArr else ArrOld()
  
  def ifScaleOptObj[A >: Null <: AnyRef](ifScale: Double, optA: OptRef[A])(f: A => CanvO): GraphicElemsOld = if (tScale < ifScale) ArrOld() else optA.fold(ArrOld(), a => ArrOld(f(a)))
    
  def ifScaleOptObjs[A >: Null <: AnyRef](ifScale: Double, optA: OptRef[A])(f: A => GraphicElemsOld): GraphicElemsOld =
    if (tScale < ifScale) ArrOld() else optA.fold(ArrOld(), f(_))
}

/** I am happy with the fundamental concept behind the OfTile traits, documentation later */
trait OfTile[TileT <: Tile, SideT <: TileSide, GridT <: TileGrid[TileT, SideT]] extends OfGridElem[TileT, SideT, GridT]
{ def tile: TileT    
  final def cood: Cood = tile.cood   
  def vertCoods: Coods = grid.vertCoodsOfTile(cood)
  def vertDispVecs: Polygon
  def cen: Vec2
  def ownSideLines: ArrOld[Line2]
}

trait OfSide[TileT <: Tile, SideT <: TileSide, GridT <: TileGrid[TileT, SideT]] extends OfGridElem[TileT, SideT, GridT]
{ def side: SideT    
  final def cood: Cood = side.cood   
  def coodsLine: CoodLine = grid.vertCoodLineOfSide(cood)
  def vertDispLine: Line2 = coodsLine.toLine2(coodToDispVec2)
  def ifTiles[A](f: (TileT, TileT) => Boolean, fA: (TileT, TileT) => A)(implicit ct: ClassTag[A]): ArrOld[A] = grid.optSidesTiles(cood) match
  { case (Some(t1), Some(t2)) => if (f(t1, t2)) ArrOld(fA(t1, t2)) else ArrOld()
    case _ => ArrOld()
  }
}