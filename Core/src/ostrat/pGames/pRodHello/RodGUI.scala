/* Copyright 2018 w0d. Licensed under Apache Licence version 2.0 */

/*inital possibles
 * cards
 * atoms
 * LED array/screen
 */
package ostrat
package pGames.pRodHello

import geom._, pCanv._, Colour._

case class RodGUI (canv: CanvasPlatform) extends CanvasSimple {
  deb("Console Debugging On..")
  val title = "reactor.."
  var myList = List[PolyFill]()
  var myGrid = new Array[Int](80)
  for (r <- 0 to 7) {
    for (c <- 0 to 9) {
//  for( r <- 0 to 7; c <- 0 to 9){
/*    canv.polyFill(Rectangle(39, 39, 40*c vv 40*r).fill(Orange))
*/
//    myGrid(c)(r) = c+9*r
    myGrid(c+9*r) = c+9*r   
    myList = Rectangle(39, 39, 40*c vv 40*r).fill(Orange) :: myList
  }
    }
//  }
  repaint(myList)
  deb("myList.length = " + myList.length)
  deb("myGrid.length = " + myGrid.length)
  for ( i <- 0 to (myGrid.length - 1) ) {
    deb("myGrid(" + i + ") = " + myGrid(i))
  }
  
  mouseUp = (v, b, s) =>
  { // mouseLoc, whichBtn, HitList
    deb(v.toString -- b.toString -- s.length.toString)
    if (s.length>0) deb(s(0).toString)
  } 


}
 //  val rndY = new scala.util.Random().nextInt(300) 
 //  val arr = Array.apply(4,5,6,7)
 //  deb(arr.apply(3).toString)
 //  val rndX = - new scala.util.Random().nextInt(300)  
 //  var ptStart: Vec2 = 200 vv rndY
 //  val t1 = TextGraphic(ptStart, title, 18, Colour.Blue)
 //  ptStart = rndX vv new scala.util.Random().nextInt(300)  
 //  val t2 = TextGraphic(ptStart, canv.getTime.toString, 18, LightBlue)
  
 //  val t3 = TextGraphic(ptStart + Vec2.apply(100, 100), canv.getTime.toString, 18, LightBlue) //dont need the new Vec2(100,100) as has factory method which Vec2.apply(100,100) == Vec()
 //  //val t3 = TextGraphic(ptStart.addXY(100, 100), canv.getTime.toString, 18, LightBlue)
 //  val t4 = TextGraphic(Vec2(new scala.util.Random().nextInt(300), new scala.util.Random().nextInt(300)), canv.getTime.toString, 18, LightBlue)
 
 //  val recty = Rectangle(300, 100, 0 vv 0).fillSubj("I am a rectangle", Green)
 
 //  val l1 = List(t1, t2)
 //  val l3 = l1 :+ t3
 //  val l4 = recty :: l1
 //  //repaints(t1, t2)
 //  repaint(l4)
 // deb("debuggin the console")

  
 // val timesUp = () => {
 //   repaint(Nil)
 //   ///    refresh
   
 //  }
 
 // canv.timeOut(timesUp, 30000)
 
 // val clicky = () => {
 //   deb("clicky")
 //   repaints(recty, TextGraphic(Vec2(-300, -300), "you chose wisely..", 18, Yellow))
 // }
 
 // mouseUp = (v, b, s) =>
 //   {
 //     clicky()
 //     deb(v.toString -- b.toString -- s.toString)} 


// case class RodGUI (canv: CanvasPlatform) extends CanvasSimple {
//   val title = "now you see it.."
//   val rndY = new scala.util.Random().nextInt(300) 
//   val arr = Array.apply(4,5,6,7)
//   deb(arr.apply(3).toString)
//   val rndX = - new scala.util.Random().nextInt(300)  
//   var ptStart: Vec2 = 200 vv rndY
//   val t1 = TextGraphic(ptStart, title, 18, Colour.Blue)
//   ptStart = rndX vv new scala.util.Random().nextInt(300)  
//   val t2 = TextGraphic(ptStart, canv.getTime.toString, 18, LightBlue)
  
//   val t3 = TextGraphic(ptStart + Vec2.apply(100, 100), canv.getTime.toString, 18, LightBlue) //dont need the new Vec2(100,100) as has factory method which Vec2.apply(100,100) == Vec()
//   //val t3 = TextGraphic(ptStart.addXY(100, 100), canv.getTime.toString, 18, LightBlue)
//   val t4 = TextGraphic(Vec2(new scala.util.Random().nextInt(300), new scala.util.Random().nextInt(300)), canv.getTime.toString, 18, LightBlue)
 
//   val recty = Rectangle(300, 100, 0 vv 0).fillSubj("I am a rectangle", Green)
 
//   val l1 = List(t1, t2)
//   val l3 = l1 :+ t3
//   val l4 = recty :: l1
//   //repaints(t1, t2)
//   repaint(l4)
//  deb("debuggin the console")

  
//  val timesUp = () => {
//    repaint(Nil)
//    ///    refresh
   
//   }
 
//  canv.timeOut(timesUp, 30000)
 
//  val clicky = () => {
//    deb("clicky")
//    repaints(recty, TextGraphic(Vec2(-300, -300), "you chose wisely..", 18, Yellow))
//  }
 
//  mouseUp = (v, b, s) =>
//    {
//      clicky()
//      deb(v.toString -- b.toString -- s.toString)} 
// }



/*
git push origin master
mill -w FxStrat.runBackground
git add -A
git commit -m "Tidying."
git push orgin master
git pull origin master
mill -w FxStrat.runBackground
git config user.name "w0d"
git config --global user.email "35996314+w0d@users.noreply.github.com"
git config --global user.name "w0d"
git add -a git commit -m "added RodTest"
git -A 
git add -A
git commit -m "added RodTest"
git push origin master

git add -A
git commit -m "minor change RodTest"
git push origin master
mill -w FxStrat.runBackground

*/
