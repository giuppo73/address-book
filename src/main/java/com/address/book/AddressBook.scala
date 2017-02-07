package com.address.book

import com.address.book.model.{AddressBookEntry, Sex}
import com.address.book.reader.AddressBookReader

object AddressBook {
  def apply()
           (implicit reader: AddressBookReader): AddressBook =
    new AddressBook()
}

class AddressBook()
                 (implicit reader: AddressBookReader) {
  private val entries = reader.toList

  def countEntriesFor(sex: Sex): Int = entries.count(_.sex == sex)

  def oldestEntry: Option[AddressBookEntry] = entries.sortBy(- _.ageInDays).headOption

  def compareAgeInDays(entryName1: String, entryName2: String): Int = {
    val entries = filterByName(entryName1, entryName2)
    val maybeEntry1 = entries.get(entryName1.toLowerCase)
    val maybeEntry2 = entries.get(entryName2.toLowerCase)
    require(maybeEntry1.nonEmpty, s"could not find $entryName1")
    require(maybeEntry2.nonEmpty, s"could not find $entryName2")
    maybeEntry1.get.ageInDays - maybeEntry2.get.ageInDays
  }

  def filterByName(names: String*): Map[String, AddressBookEntry] = {
    val namesSet = names.map(_.toLowerCase).toSet

    val filteredEntries =
      for {
        entry <- entries
        matching = entry.name.split(" ").map(_.trim.toLowerCase).filter(namesSet.contains) if matching.nonEmpty
      } yield {
        matching.head -> entry
      }

    Map(filteredEntries:_*)
  }
}
