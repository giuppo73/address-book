package com.address.book.exception

case class FileFormatException(message: String, cause:Option[Throwable])
  extends RuntimeException(message, cause.orNull)
