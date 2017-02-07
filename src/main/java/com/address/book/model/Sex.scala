package com.address.book.model

object Sex {
  def apply(sexString: String): Sex = {
    if (sexString.equalsIgnoreCase(SexMale.asString))
      SexMale
    else if (sexString.equalsIgnoreCase(SexFemale.asString))
      SexFemale
    else
      throw new RuntimeException(s"invalid sex: $sexString")
  }
}

trait Sex {
  def asString: String

  override def toString: String = asString
}

case object SexMale extends Sex {
  override def asString = "male"
}

case object SexFemale extends Sex {
  override def asString = "female"
}
