package com.address.book.reader

import com.address.book.exception.FileFormatException
import com.address.book.model.AddressBookEntry

import scala.io.Source

object AddressBookReader {
  def apply(inputFile: String): AddressBookReader =
    new AddressBookReader(Source.fromFile(inputFile))

  def apply(source: Source): AddressBookReader =
    new AddressBookReader(source)
}

class AddressBookReader private (source: Source) extends Iterable[AddressBookEntry] {
  override def iterator: Iterator[AddressBookEntry] = {
    new AddressBookReaderIterator(source.getLines())
  }

  /**
    * This is a simple csv reader, but it doesn't conform to the csv specs:
    * there's no support for quoted values and escaped characters.
    */
  private class AddressBookReaderIterator (lines: Iterator[String]) extends Iterator[AddressBookEntry] {
    override def hasNext: Boolean = lines.hasNext
    override def next(): AddressBookEntry = {
      val nextLine = lines.next()
      val values = nextLine.split(",").toList.map(_.trim)
      try {
        AddressBookEntry(values)
      } catch {
        case e: Exception =>
          throw FileFormatException(s"invalid format for line: $nextLine:\n${e.getMessage}", Some(e))
      }
    }
  }

}






