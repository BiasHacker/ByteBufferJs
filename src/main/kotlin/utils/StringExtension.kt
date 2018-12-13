package utils

@ExperimentalUnsignedTypes
fun String.toUTF8Bytes(): List<Byte> {
    val bytes = mutableListOf<Byte>()

    var index = 0
    while (index < length) {
        var charcode = this[index].toInt()
        if (charcode < 0x80) {
            bytes.add(charcode.toByte())
        } else if (charcode < 0x800) {
            bytes.add((0xc0 or (charcode shr 6)).toByte())
            bytes.add((0x80 or (charcode and 0x3f)).toByte())
        } else if (charcode < 0xd800 || charcode >= 0xe000) {
            bytes.add((0xe0 or (charcode shr 12)).toByte())
            bytes.add((0x80 or ((charcode shr 6) and 0x3f)).toByte())
            bytes.add((0x80 or (charcode and 0x3f)).toByte())
        } else {
            index++
            charcode = 0x10000 + (((charcode and 0x3ff) shl 10) or (this[index].toInt() and 0x3ff))
            bytes.add((0xf0 or (charcode shr 18)).toByte())
            bytes.add((0x80 or ((charcode shr 12) and 0x3f)).toByte())
            bytes.add((0x80 or ((charcode shr 6) and 0x3f)).toByte())
            bytes.add((0x80 or (charcode and 0x3f)).toByte())
        }
        index++
    }

    return bytes.toList()
}