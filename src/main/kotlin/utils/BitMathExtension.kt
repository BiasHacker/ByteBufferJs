package utils

infix fun Byte.shl(bitCount: Int) = this.toInt() shl bitCount

infix fun Byte.shr(bitCount: Int) = this.toInt() shr bitCount

infix fun Short.shl(bitCount: Int) = this.toInt() shl bitCount

infix fun Short.shr(bitCount: Int) = this.toInt() shr bitCount
