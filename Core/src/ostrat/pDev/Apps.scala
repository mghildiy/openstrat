/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDev
import pCanv._, pStrat._

/** This file is normally set by "git update-index --assume-unchanged Core/src/ostrat/pDev/Apps.scala" this is to allow the developer to make local
 *  changes to appNum without modifying the master project. If permanent changes are required run:
 *  "git update-index --no-assume-unchanged Core/src/ostrat/pDev/Apps.scala". Stage and commit the changes and then run:
 *  "git update-index --assume-unchanged Core/src/ostrat/pDev/Apps.scala" before pushing or pull-requesting changes." */
object Apps
{
  val theMap: Map[Int, (CanvasPlatform => Unit, String)] = Map(
      (0, (new pGames.pCiv.CivGui(_), "ScalaFx Rise of Civs")),
      (1, (pGames.pWW2.WWIIGui(_, pGames.pWW2.WW1940), "World War II") ),
      (2, (pGames.p1783.Y1783Gui(_, pGames.p1783.Nap1), "1783")),
      (3, (FlagsGui(_), "ScalaFx Flags")),
      (4, (pGames.pSpace.Planets(_), "ScalaFx Planets")),
      (5, (pEarth.pFlat.FlatEarthGui(_), "Flat Earth")),
      (6, (new pGames.pDung.DungeonGui(_), "ScalaFx Dungeon")),
      (7, (pGames.pCloseOrder.BattleGui(_, pGames.pCloseOrder.Nap1), "ScalaFx Formation")),         
      (8, (ColourGen(_), "ScalaFx Some Colours")),
      (9, (pGames.p305.BC305Gui(_, pGames.p305.Bc1), "BC 305")),
      (10, (pGames.pCard.BlackJack(_), "ScalaFx BlackJack")),
      (11, (new pGames.pChess.DraughtsGui(_), "Draughts")),
      (21, (learn.Lesson1(_), "ScalaFx Demonstration Canvas 1")),
      (22, (learn.Lesson2(_), "ScalaFx Demonstration Canvas 2")),
      (23, (learn.Lesson3(_), "ScalaFx Demonstration Canvas 3")),
      (24, (learn.Lesson4(_), "ScalaFx Demonstration Canvas 4")),
      (25, (learn.Lesson5(_), "ScalaFx Demonstration Canvas 5")),
      (26, (learn.Lesson6(_), "ScalaFx Demonstration Canvas 6")),
      
      (30, (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame1, pGames.pZug.PlayBritain), "ScalaFx Zugfuhrer Z1 Britain")),
      (31, (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame1, pGames.pZug.PlayGermany), "ScalaFx Zugfuhrer Z1 Germany")),
      (32, (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame1, pGames.pZug.PlayGermanyBritain), "ScalaFx Zugfuhrer Z1 Play both")),
      (33, (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame2, pGames.pZug.PlayGermanyFrance), "ScalaFx Zugfuhrer Z2 Play both")),
 
      (66, (new pGames.pRodHello.RodGUI(_), "nothing to say so far..")),
  )
  /** Change appNum to change the loaded application. */
  val appNum: Int = 21
  def curr: (CanvasPlatform => Unit, String) = theMap(appNum)
}