package utils

import org.khronos.webgl.Uint8Array

external fun decodeURIComponent(input: String): String

@JsName("byteListToUint8Array")
fun byteListToUint8Array(array: List<Byte>) = Uint8Array(array.toTypedArray())

@ExperimentalUnsignedTypes
fun  List<Byte>.toUTF8String(): String {
    return decodeURIComponent(this.joinToString("") {
        "%" + it.toUByte().toString(16)
    })
}
