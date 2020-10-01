/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** This lesson is working, but has what looks like a very dubious implementation. */
case class LsC4(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C4")
{ val r0 = Rectangle(200, 100)//.fill(Red)
  val r1 = r0.slate(-300,300)
  val r2 = r0.slate(300, 300)
  val r3 = r0.slate(300, -300)
  val r4 = r0.slate(-300, -300)
  val rArr = Arr(r1, r2, r3, r4)
  var colour = Red
  def gArr = rArr.map(r => r.fillActive(colour, r.polyCentre))
  val textPosn = 0 vv 0
  val startText = TextGraphic("Click on the rectangles to cycle the colour.", 28, textPosn)
  deb((gArr +- startText).length.toString)
  repaint(gArr +- startText)
  
  /** Note you can use what names you like. Here I put the types explicitly for clarity. When you are familiar with an anonymous function, you will
   *  probably want to use a short parameter list like (v, b, s).  */
  mouseUp = (button: MouseButton, selected: List[Any], posn: Vec2) => selected match
  {
    case ::(cen: Vec2, tail) =>
    { colour = colour.nextFrom(Colours(Red, Orange, Green))
      repaint(gArr +- startText)
    }
      
    case _ =>  deb("Missed")
  }
}