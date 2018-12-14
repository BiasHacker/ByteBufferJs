import utils.*
import utils.toUTF8String

@ExperimentalUnsignedTypes
@JsName("ByteBuffer")
class ByteBuffer {
    var bytes: MutableList<Byte> = mutableListOf()
    var position: Int = 0

    val size: Int
        get() = bytes.size

    fun clear() {
        position = 0
        bytes.clear()
    }

    fun readBytes(len: Int): List<Byte> {
        val data = bytes.subList(position, position + len)
        position += len
        return data
    }

    fun readByte(): Byte {
        return bytes[position++]
    }

    fun readBoolean(): Boolean {
        return readByte().toInt() > 0
    }

    fun readDouble(): Double {
        var value = 0UL
        for (i in 56 downTo 0 step 8) {
            value += readUnsignedByte().toULong() shl i
        }
        return Double.fromBits(value.toLong())
    }

    fun readFloat(): Float {
        var value = 0
        for (i in 24 downTo 0 step 8) {
            value += readUnsignedByte() shl i
        }
        return Float.fromBits(value)
    }

    fun readInt(): Int {
        var value = 0
        for (i in 24 downTo 0 step 8) {
            value += readUnsignedByte() shl i
        }
        return value
    }

    fun readShort(): Short {
        var value: Short = 0
        for (i in 8 downTo 0 step 8) {
            val s = (readUnsignedByte() shl i).toShort()
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
        return readUTFBytes(readShort().toInt())
    }

    fun readUTFBytes(length: Int): String {
        return readBytes(length).toUTF8String()
    }

    fun writeBytes(value: List<Byte>) {
        bytes.addAll(value)
        position += value.size
    }

    fun writeByte(value: Byte) {
        bytes.add(value)
        position += 1
    }

    fun writeBoolean(value: Boolean) {
        writeByte(if (value) 1 else 0)
    }

    fun writeDouble(value: Double) {
        val intBits = value.toBits()
        for (i in 56 downTo 0 step 8) {
            writeByte((intBits shr i and 0xFF).toByte())
        }
    }

    fun writeFloat(value: Float) {
        val intBits = value.toBits()
        for (i in 24 downTo 0 step 8) {
            writeByte((intBits shr i and 0xFF).toByte())
        }
    }

    fun writeInt(value: Int) {
        for (i in 24 downTo 0 step 8) {
            writeByte((value shr i and 0xFF).toByte())
        }
    }

    fun writeShort(value: Short) {
        for (i in 8 downTo 0 step 8) {
            writeByte((value shr i and 0xFF).toByte())
        }
    }

    fun writeUnsignedInt(value: UInt) {
        for (i in 24 downTo 0 step 8) {
            writeByte((value shr i and 0xFFU).toByte())
        }
    }

    fun writeUTF(value: String) {
        val hex = value.toUTF8Bytes()
        writeShort(hex.size.toShort())
        writeBytes(hex)
    }

    fun writeUTFBytes(value: String) {
        writeBytes(value.toUTF8Bytes())
    }

    override fun toString() = bytes.joinToString(" ") {
        it.toUByte().toString(16).padStart(2, '0').toUpperCase()
    }
}