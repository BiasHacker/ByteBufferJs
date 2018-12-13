package utils

external fun decodeURIComponent(input: String): String

@ExperimentalUnsignedTypes
fun  List<Byte>.toUTF8String(): String {
    return decodeURIComponent(this.joinToString("") {
        "%" + it.toUByte().toString(16)
    })
}

