package com.address.book

import com.address.book.model.SexMale
import com.address.book.reader.AddressBookReader

object Main {
  def main(args: Array[String]): Unit = {
    require(args.length == 1, "Usage: sbt run <address-book-file>")
    implicit val reader = AddressBookReader(args.head)
    val addressBook = AddressBook()
    println(s"num males: ${addressBook.countEntriesFor(SexMale)}")
    println(s"oldest entry: ${addressBook.oldestEntry}")
    println(s"difference in age between Bill and Paul: ${addressBook.compareAgeInDays("Bill", "Paul")}")
  }
}
