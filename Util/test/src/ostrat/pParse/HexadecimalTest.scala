package ostrat
package pParse
import utest._

object HexadecimalTest extends TestSuite
{
  val a1 = "0x44".toChars
  val h1: EMon[(CharsOff, TextPosn, IntHexaToken)] = Hexadecimal(a1.charsOffsetter, StrPosn())(a1)

  val tests = Tests
  {
    'Test1
    {
      assertMatch(h1){case Good((CharsOff(4), StrPosn(1, 5), IntHexaToken(_, _, 68))) => }
    }
  }
}