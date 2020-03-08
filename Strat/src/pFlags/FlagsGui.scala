/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags
import geom._, pCanv._, Colour._

case class FlagsGui(canv: CanvasPlatform) extends CanvasNoPanels("Flags Gui")
{
  backColour = Gray
   
  val tlFlags = Refs(Armenia, Austria, England, UnitedKingdom, Japan)
  val tlObjs = tlFlags.iMap((el, i) => el.subj.scale(100).topLeft.slate(canv.topLeft.subY(i * 110)))

  val trFlags = Refs(Belgium, Chad, France)
  val trObjs = trFlags.iMap((el, i) => el.subj.scale(100).topRight.slate(canv.topRight.subY(i * 110)))

  val blFlags = Refs(China, Italy)
  val blObjs = blFlags.iMap((el, i) => el.subj.scale(100).bottomLeft.slate(canv.bottomLeft.addY(i * 110)))

  val brFlags = Refs(Germany, Germany1871, Ireland)
  val brObjs = brFlags.iMap((el, i) => el.subj.scale(100).bottomRight.slate(canv.bottomRight.addY(i * 110)))

  val cenFlags = Refs(UnitedStates, Russia, USSR)
  val cenObjs = cenFlags.iMap((el, i) => el.subj.scale(100).slateY(200 - i * 110))

  val stuff = tlObjs ++ trObjs ++ blObjs ++ brObjs ++ cenObjs

  mouseUp = (v, b, s) =>
  { val str: String = s.headToStringElse("No clickable object on canvas")
    val tg = TextGraphic(str, 28, 0 vv 100)
    repaint(stuff -+  tg)
  }

  repaint(stuff)
}