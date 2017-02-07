package com.address.book.model

import org.specs2.execute.Result
import org.specs2.mutable.Specification

class SexSpec extends Specification {
  "A sex object" should {
    "return SexMale for case insensitive occurrences of the word 'male'" in maleTest
    "return SexFemale for case insensitive occurrences of the word 'female'" in femaleTest
    "fail to return a Sex for anything else" in invalidTest
  }

  private def maleTest =
    Result.foreach(provideCaseVariationsOf("male")) { maleString =>
      Sex(maleString) should beEqualTo(SexMale)
    }

  private def femaleTest =
    Result.foreach(provideCaseVariationsOf("female")) { femaleString =>
      Sex(femaleString) should beEqualTo(SexFemale)
    }

  private def invalidTest =
    Result.foreach(List("whatever", "something else")) { invalidString =>
      Sex(invalidString) should throwA[RuntimeException]
    }

  private def provideCaseVariationsOf(s: String) = {
    for {
      mask <- 0 until (1 << s.length)
    } yield {
      val chars =
        for {
          (char, charPosition) <- s.toLowerCase.toCharArray.zipWithIndex
          maskValue = 1 << charPosition
          maskForChar = maskValue & mask
          makeItUpper = maskForChar != 0
        } yield {
          if (makeItUpper)
            char.toUpper
          else
            char
        }
      chars.mkString
    }
  }

}
