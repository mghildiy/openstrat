/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse
/** This trait includes all the tokens except braces plus the Bracket syntactic blocks. The Block in the name block member is either the top level
 *  statements in the file or the statements with in a bracket block. Conceptually the source file is considered a special case of bracket block where 
 *  the beginning of substitutes for the opening bracket and the end of file substitutes for the closing bracket. BlockMember has only two sub traits
 *  StatementMember and SemiToken. So a sequence of TokenOrBlocks is simply a series of tokens which has been parsed into Blocks. */
trait TokenOrBlock extends TextSpan

/** A block member is either a statement member or the semi colon Statement delimiter. Statement members includes inner blocks. */
trait BlockMember extends TokenOrBlock

/** A statemement member is ant Token except the semi colon, which is the statement delimiter and the braces, plus it includes blocks, As blocks
 * contain a sequence of Statements but can themselves be part of a statement. */
trait StatementMember extends BlockMember

/** I think its helpful to have an expression member trait for syntax that is not expressions. So I don't think it would be helpful if say an opening brace was an
 *  expression. All Expressions are Expression members. */
trait ExprMember extends StatementMember

/** A Token that can be a member of an expression. */
trait ExprMemberToken extends BlockMemberToken with ExprMember

sealed trait BracketToken extends Token
sealed trait BracketOpen extends BracketToken
{
   def matchingBracket(bc: BracketClose): Boolean
   def newBracketBlock(cb: BracketClose, statements: Refs[Statement]): BracketBlock
}

/** A closing bracket Token. */
sealed trait BracketClose extends BracketToken

case class ParenthOpen(startPosn: TextPosn) extends BracketOpen  
{ def srcStr = "("
  override def matchingBracket(bc: BracketClose): Boolean = bc.isInstanceOf[ParenthClose]
  override def newBracketBlock(cb: BracketClose, statements: Refs[Statement]): BracketBlock = (ParenthBlock(statements, this, cb))
  override def tokenTypeStr: String = "ParenthOpenToken"
}

case class ParenthClose(startPosn: TextPosn) extends BracketClose
{ def srcStr = ")"
  override def tokenTypeStr: String = "ParenthCloseToken"
}

case class SquareOpen(startPosn: TextPosn) extends BracketOpen
{ def srcStr = "["
  override def matchingBracket(bc: BracketClose): Boolean = bc.isInstanceOf[SquareClose]
  override def newBracketBlock(cb: BracketClose, statements: Refs[Statement]): BracketBlock = (SquareBlock(statements, this, cb))
  override def tokenTypeStr: String = "SquareOpenToken"
}

case class SquareClose(startPosn: TextPosn) extends BracketClose
{ def srcStr = "]"
  override def tokenTypeStr: String = "SquareCloseToken"
}

case class CurlyOpen(startPosn: TextPosn) extends BracketOpen
{ def srcStr = "{"
  override def matchingBracket(bc: BracketClose): Boolean = bc.isInstanceOf[CurlyClose]
  override def newBracketBlock(cb: BracketClose, statements: Refs[Statement]): BracketBlock = (CurlyBlock(statements, this, cb))
  override def tokenTypeStr: String = "CurlyOpenToken"
}
case class CurlyClose(startPosn: TextPosn) extends BracketClose
{ def srcStr = "}"
  override def tokenTypeStr: String = "CurlyCloseToken"
}

sealed trait Braces
{ def openChar: Char
  def closeChar: Char
  def braceName: String
}
case object RoundBraces extends Braces
{ override def openChar: Char = '('
  override def closeChar: Char = ')'
  override def braceName: String =  "Round"
}

case object SquareBraces extends Braces
{ override def openChar: Char = '['
  override def closeChar: Char = ']'
  override def braceName: String = "Square"
}

case object CurlyBraces extends Braces
{ override def openChar: Char = '{'
  override def closeChar: Char = '}'
  override def braceName: String = "Curly"
}

sealed trait BracketBlock extends StatementsHolder
{ def startBracket: BracketOpen
  def endBracket: BracketClose
}

case class ParenthBlock(statements: Refs[Statement], startBracket: BracketOpen, endBracket: BracketClose) extends BracketBlock
{ override def exprName: String = "ParenthBlock" }

case class SquareBlock(statements: Refs[Statement], startBracket: BracketOpen, endBracket: BracketClose) extends BracketBlock
{ override def exprName: String = "SquareBlock" }

case class CurlyBlock(statements: Refs[Statement], startBracket: BracketOpen, endBracket: BracketClose) extends BracketBlock
{ override def exprName: String = "CurlyBlock" }