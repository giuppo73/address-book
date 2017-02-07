package com.address.book.reader

import com.address.book.exception.FileFormatException
import org.specs2.execute.Result
import org.specs2.mutable.Specification

import scala.io.Source

class AddressBookReaderSpec extends Specification {
  "An AddressBookReader object" should {
    "be created from any source" in generationTest
    "read all of the entries from valid sources" in validReadingTest
    "fail with an exception on invalid sources" in invalidReadingTest
  }

  private def generationTest =
    Result.foreach(provideValidAddressBookResources.map(Source.fromResource(_))) { validSource =>
      AddressBookReader(validSource)
      ok
    }

  private def validReadingTest =
    Result.foreach(provideValidAddressBookResources) { validResource =>
      val reader = AddressBookReader(Source.fromResource(validResource))
      val actualEntries = reader.toList
      val expectedEntries = Source.fromResource(validResource).getLines()
      actualEntries.length should beEqualTo(expectedEntries.length)
    }

  private def invalidReadingTest =
    Result.foreach(provideInvalidAddressBookResources.map(Source.fromResource(_))) { invalidSource =>
      val reader = AddressBookReader(invalidSource)
      reader.toList should throwA[FileFormatException]
    }


  private val provideValidAddressBookResources: List[String] =
    List(
      "valid-address-books/AddressBook"
    )

  private val provideInvalidAddressBookResources: List[String] =
    List(
      "invalid-address-books/InvalidAddressBook1"
    )
}
