import utils.*
import utils.toUTF8String

@ExperimentalUnsignedTypes
@JsName("ByteBuffer")
class ByteBuffer {
    var bytes: MutableList<Byte> = mutableListOf()
    var position: Int = 0

    @JsName("size")
    val size: Int
        get() = bytes.size

    @JsName("clear")
    fun clear() {
        position = 0
        bytes.clear()
    }

    @JsName("new")
    fun new(data: Array<Byte>) {
        position = 0
        bytes = data.toMutableList()
    }

    @JsName("readBytes")
    fun readBytes(len: Int): List<Byte> {
        val data = bytes.subList(position, position + len)
        position += len
        return data
    }

    @JsName("readByte")
    fun readByte(): Byte {
        return bytes[position++]
    }

    @JsName("readBoolean")
    fun readBoolean(): Boolean {
        return readByte().toInt() > 0
    }

    @JsName("readDouble")
    fun readDouble(): Double {
        var value = 0UL
        for (i in 56 downTo 0 step 8) {
            value += readUnsignedByte().toULong() shl i
        }
        return Double.fromBits(value.toLong())
    }

    @JsName("readFloat")
    fun readFloat(): Float {
        var value = 0
        for (i in 24 downTo 0 step 8) {
            value += readUnsignedByte() shl i
        }
        return Float.fromBits(value)
    }

    @JsName("readInt")
    fun readInt(): Int {
        var value = 0
        for (i in 24 downTo 0 step 8) {
            value += readUnsignedByte() shl i
        }
        return value
    }

    @JsName("readShort")
    fun readShort(): Short {
        var value: Short = 0
        for (i in 8 downTo 0 step 8) {
            val s = (readUnsignedByte() shl i).toShort()
            value = (value + s).toShort()
        }
        return value
    }

    @JsName("readUnsignedByte")
    fun readUnsignedByte(): UByte {
        return readByte().toUByte()
    }

    @JsName("readUnsignedInt")
    fun readUnsignedInt(): UInt {
        return readInt().toUInt()
    }

    @JsName("readUnsignedShort")
    fun readUnsignedShort(): UShort {
        return readShort().toUShort()
    }

    @JsName("readUTF")
    fun readUTF(): String {
        return readUTFBytes(readShort().toInt())
    }

    @JsName("readUTFBytes")
    fun readUTFBytes(length: Int): String {
        return readBytes(length).toUTF8String()
    }

    @JsName("writeBytes")
    fun writeBytes(value: List<Byte>) {
        value.forEach(::writeByte)
    }

    @JsName("writeByte")
    fun writeByte(value: Byte) {
        if (bytes.size <= position) {
            bytes.addAll((0..position - bytes.size)
                    .map { 0.toByte() }.toList())
        }
        bytes[position++] = value
    }

    @JsName("writeBoolean")
    fun writeBoolean(value: Boolean) {
        writeByte(if (value) 1 else 0)
    }

    @JsName("writeDouble")
    fun writeDouble(value: Double) {
        val intBits = value.toBits()
        for (i in 56 downTo 0 step 8) {
            writeByte((intBits shr i and 0xFF).toByte())
        }
    }

    @JsName("writeFloat")
    fun writeFloat(value: Float) {
        val intBits = value.toBits()
        for (i in 24 downTo 0 step 8) {
            writeByte((intBits shr i and 0xFF).toByte())
        }
    }

    @JsName("writeInt")
    fun writeInt(value: Int) {
        for (i in 24 downTo 0 step 8) {
            writeByte((value shr i and 0xFF).toByte())
        }
    }

    @JsName("writeShort")
    fun writeShort(value: Short) {
        for (i in 8 downTo 0 step 8) {
            writeByte((value shr i and 0xFF).toByte())
        }
    }

    @JsName("writeUnsignedInt")
    fun writeUnsignedInt(value: UInt) {
        for (i in 24 downTo 0 step 8) {
            writeByte((value shr i and 0xFFU).toByte())
        }
    }

    @JsName("writeUTF")
    fun writeUTF(value: String) {
        val hex = value.toUTF8Bytes()
        writeShort(hex.size.toShort())
        writeBytes(hex)
    }

    @JsName("writeUTFBytes")
    fun writeUTFBytes(value: String) {
        writeBytes(value.toUTF8Bytes())
    }

    @JsName("toJsonString")
    fun toJsonString(): String {
        return JSON.stringify(bytes)
    }

    override fun toString() = bytes.joinToString(" ") {
        it.toUByte().toString(16).padStart(2, '0').toUpperCase()
    }
}