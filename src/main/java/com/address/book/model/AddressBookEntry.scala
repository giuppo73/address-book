package com.address.book.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object AddressBookEntry {

  def apply(values: List[String]): AddressBookEntry = {
    require(values.size == 3, s"expected 3 columns, found ${values.size}")
    values match {
      case name :: sex :: dob :: Nil =>
        val parsedDob = LocalDate.parse(dob, dateFormatter)
        val adjustedDob = adjustDob(parsedDob)
        new AddressBookEntry(
          name = name,
          sex = Sex(sex),
          dob = adjustedDob)
      case _ =>
        throw new IllegalArgumentException(s"invalid values for an AddressBookEntry: ${values.mkString(",")}")
    }
  }

  def adjustDob(dob: LocalDate) = {
    if (dob.compareTo(LocalDate.now()) > 0)
      dob.minusYears(100)
    else
      dob
  }

  private val dateFormatter: DateTimeFormatter= DateTimeFormatter.ofPattern("dd/MM/yy")
}

case class AddressBookEntry(name: String, sex: Sex, dob: LocalDate) {
  require(name.nonEmpty, "empty address book entry found")
  require(dob.compareTo(LocalDate.now()) < 0, s"invalid date of birth for $name: $dob")

  lazy val ageInDays: Int = ChronoUnit.DAYS.between(dob, LocalDate.now()).toInt

  override def toString: String = s"$name, $sex, $dob"
}
