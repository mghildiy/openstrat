package ostrat
package gOne
import pCanv._, geom._, pGrid._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform, scen: OneGrid) extends CmdBarGui("Game One Gui")
{ var statusText = "Let click on Player to select. Right click on adjacent Hex to set move."
  implicit val grid = scen.grid
  val players = scen.players
  var moves: OptRefs[HTStep] = grid.newOptRefs[HTStep]

  val scale = grid.fullDisplayScale(mainWidth, mainHeight)

  val lunits = grid.mapArrOptRefVec(OneGrid1.players){ (r, v, p) =>
    Rectangle(0.9, 0.6, v).fillDrawTextActive(p.colour, RPlayer(p, r), p.toString, 24, 2.0)
  }

  val tiles = grid.activeTiles
  val roardTexts = grid.cenSideVertRoordText

  val sls = grid.sidesDraw(2.0)

  def mMoves = moves.gridMapSomes{(r, step) =>
    val newR = r + step.roord
   // val v = grid.roordToVec2Rel(newR, scale)
    RoordLine(r, newR).toLine2(grid.roordToVec2Rel(_, scale)).draw(2, players.gridElemGet(r).colour)
  }

  def thisTop(): Unit = reTop(Refs(status))

  mainMouseUp = (b, cl, _) => (b, cl, selected) match
    { case (LeftButton, cl, _) =>
      { selected = cl
        statusText = selected.headToStringElse("Nothing Selected")
        thisTop()
      }

      case (RightButton, (t : HexTile) :: _, RPlayer(p, r) :: l) =>
      {
        val newM = t.adjOf(r)//.foldDo() .foreach{ ht =>
        moves.set(grid.index(r), newM)
        repaint()
      }
       case (_, h, _) => deb("Other; " + h.toString)
    }
  thisTop()
  def frame = (tiles +- sls ++ roardTexts ++ lunits).gridTrans(scale)
  def repaint() = mainRepaint(frame ++ mMoves)
  repaint()
}