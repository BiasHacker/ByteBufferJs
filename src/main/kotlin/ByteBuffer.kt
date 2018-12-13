import utils.shl
import utils.toUTF8String

@ExperimentalUnsignedTypes
@JsName("ByteBuffer")
class ByteBuffer() {
    var bytes: List<Byte> = mutableListOf()
    var position: Int = 0

    fun readBytes(len: Int): List<Byte> {
        val data = bytes.subList(position, len)
        position += len
        return data
    }

    fun readBoolean(): Boolean {
        return bytes[position++].toInt() > 0
    }

    fun readByte(): Byte {
        return bytes[position++]
    }

    fun readDouble(): Double {
        var value = 0L
        for (i in 56 downTo 0 step 8) {
            value += readByte() shl i
        }
        return Double.fromBits(value)
    }

    fun readFloat(): Float {
        var value = 0
        for (i in 24 downTo 0 step 8) {
            value += readByte() shl i
        }
        return Float.fromBits(value)
    }

    fun readInt(): Int {
        var value = 0
        for (i in 24 downTo 0 step 8) {
            value += readByte() shl i
        }
        return value
    }

    fun readShort(): Short {
        var value: Short = 0
        for (i in 8 downTo 0 step 8) {
            val s = (readByte() shl i).toShort()
            value = (value + s).toShort()
        }
        return value
    }

    fun readUnsignedByte(): UByte {
        return readByte().toUByte()
    }

    fun readUnsignedInt(): UInt {
        return readInt().toUInt()
    }

    fun readUnsignedShort(): UShort {
        return readShort().toUShort()
    }

    fun readUTF(): String {
        return readUTFBytes(readUnsignedShort().toInt())
    }

    fun readUTFBytes(length: Int): String {
        return readBytes(length).toUTF8String()
    }

    override fun toString() = bytes.joinToString("") {
        it.toUByte().toString(16)
    }


}