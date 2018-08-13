/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDisp
import geom._

trait Dist2LikeGui extends MapGui
{
   /** The Distance represented by one pixel width / height on the screen */
   var scale: Dist
   val margin = 35
   
   //(canv.width.subMin(margin, 20) / mapWidth).min(canv.height.subMin(margin, 20) / mapHeight)  
   def scaleMax: Dist
   def scaleMin: Dist //= scaleAlignedMin.min(10.millionMiles)

   var mapFocus: Dist2 = Dist2(0.km, 0.km)
   //@inline def setFocus(x: Distouble, y: Double): Unit = mapFocus = Vec2(x, y)
  
   def scaleAlignedMin: Dist = ???//mapPanelDiameter / mapWidth.max(mapHeight).max(0.000001)
   def scaleRotatedMin: Dist = ???//(mapWidth.squared + mapHeight.squared) / mapWidth.max(mapHeight).max(0.000001)
   
   val bZoomIn = button1("+", zoomInCmd)
   val bZoomOut = button1("-", zoomOutCmd)
   val zoomable: Seq[ShapeSubj] = Seq(bZoomIn, bZoomOut) 

   val bMapRotateClockwise = button1("\u21BB", () => { rotation += Angle.deg(-20); repaintMap() } )
   val bMapRotateAntiClockwise = button1("\u21BA", () => { rotation += Angle.deg(20); repaintMap() } )
  // val focusAdjust = Seq(bFocusLeft, bFocusRight, bFocusUp, bFocusDown, bMapRotateClockwise, bMapRotateAntiClockwise)
   val adjustZoom: Seq[ShapeSubj] = zoomable //++ focusAdjust
   def zoomInCmd = () => { scale = (scale / 1.5).max(scaleMin); repaintMap }
   def zoomOutCmd = () => { scale = (scale * 1.5).min(scaleMax); repaintMap }
   //def moveInc = 40 /scale
   /** Translates a point from map position to Canvas Display position */   
   def toCanv(mapPoint: Dist2): Vec2 = (mapPoint - mapFocus).rotate(rotation) / scale     
   
   /** Translates a point from Canvas Display position back to Map position */
   def invCanv(canvPoint: Vec2): Vec2 = ???//(canvPoint / scale).rotate(-rotation) + mapFocus   
   /** Translates an array of map points to an array of Canvas Display positions */
   def arrCanv(inp: Dist2s): Vec2s = inp.pMap(toCanv(_))
   final def repaintMap(): Unit =
   {
      val o2 = mapObjs      
      mapPanel.repaint(o2)
   }
//   final def repaintMap(newObjs: Seq[CanvObj[_]]): Unit =
//   {
//      mapObjs = newObjs
//      repaintMap
//   }   
   
   def reFocus(newFocus: Dist2): Unit =
   {
      mapFocus = newFocus
      repaintMap
   }
   def adjFocus(adj: Dist2): Unit = reFocus(mapFocus + adj)
   var rotation: Angle = deg0
   implicit class ImpVec2InCanvasMap(thisVec2: Vec2)
   {
      def mapRotate: Vec2 = thisVec2.rotate(rotation)
      def antiMapRotate: Vec2 = thisVec2.rotate(- rotation)
   }
   
}