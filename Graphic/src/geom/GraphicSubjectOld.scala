/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred 
 *  margin space. Not sure about the name. not sure if the trait is useful. */
@deprecated trait GraphicSubjectOld extends GraphicElem with GraphicActive
{ def cen: Vec2
  def elems: ArrOld[PaintElem]
  def tL: GraphicSubjectOld = this.slate(boundingRect.bottomRight)
  def tR: GraphicSubjectOld = this.slate(boundingRect.bottomLeft)
  def bL: GraphicSubjectOld = this.slate(boundingRect.topRight)
  def bR: GraphicSubjectOld = this.slate(boundingRect.topLeft)
  def width: Double = boundingRect.width
  def addElems(newElems: ArrOld[PaintElem]): GraphicSubjectOld
  def addElem(newElem: PaintElem): GraphicSubjectOld = addElems(ArrOld(newElem))
  def mutObj(newObj: AnyRef): GraphicSubjectOld
}

