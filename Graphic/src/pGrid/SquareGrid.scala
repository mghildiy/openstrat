package ostrat
package pGrid
import geom._

/** Currently all SquareGrids are regular. */
case class SquareGrid(cTileMin: Int, cTileMax: Int, yTileMin: Int, yTileMax: Int) extends TileGridReg
{
  final override def xCen: Double = (cTileMin + cTileMax) / 2.0
  override def coodToVec2(cood: Cood): Vec2 = Vec2(cood.c, cood.y)
  override def sideCoodToCoodLine(sideCood: ostrat.pGrid.Cood): CoodLine = SquareGrid.sideCoodToCoodLine(sideCood)
  //def cen = Vec2(xCen, yCen)

  def rowTileLen: Int = ((cTileMax.roundDownToEven - cTileMin.roundUpToEven + 2) / 2).min0
  def numOfRows: Int = ((yTileMax.roundDownToEven - yTileMin + 2) / 2).min0
  def numOfTiles: Int = numOfRows * numOfTiles

  override def tilesAllForeach(f: Cood => Unit): Unit = ijToForeach(yTileMin, yTileMax, 2)(cTileMin, cTileMax, 2)((y, x) => f(Cood(x, y)))
  @inline override def index(c: Int, y: Int): Int = (y - yTileMin) / 2 * rowTileLen + (c - cTileMin) / 2

  @inline override def sideCoodsOfTile(tileCood: Cood): Coods = SquareGrid.sideCoodsOfTile(tileCood)
}

object SquareGrid
{
  val vertCoodsOfTile00: Coods = Coods(1 cc 1,  1 cc -1,  -1 cc -1, -1 cc 1)
  def vertCoodsOfTile(x: Int, y: Int): Coods = vertCoodsOfTile(x cc y)
  def vertCoodsOfTile(inp: Cood): Coods = vertCoodsOfTile00.pMap(inp + _)
  val adjTileCoodsOfTile00: Coods = Coods(0 cc 2, 2 cc 2, 2 cc 0, 2 cc -2, 0 cc -2, -2 cc -2, -2 cc 0)
  def adjTileCoodsOfTile(tileCood: Cood): Coods = adjTileCoodsOfTile00.pMap(tileCood + _)
  def sideCoodsOfTile(inp: Cood): Coods = Coods(inp.addY(1), inp.addX(1), inp.subY(1), inp.subY(1))

  sealed trait PathDirn
  object Rt extends PathDirn
  object Lt extends PathDirn
  object Up extends PathDirn
  object Dn extends PathDirn

  def sideCoodToCoodLine(sideCood: Cood): CoodLine = sideCoodToCoodLine(sideCood.c, sideCood.y)

  def sideCoodToCoodLine(x: Int, y: Int): CoodLine = (x %% 2, y %% 2) match
  { case (1, 0) => CoodLine(x, y + 1, x, y - 1)
    case (0, 1)=> CoodLine(x - 1, y, x + 1, y)
    case _ => excep("Invalid Square Cood for a side")
  }
}