import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utils.toUTF8Bytes
import kotlin.browser.document

@ExperimentalUnsignedTypes
@JsName("main")
fun main(args: Array<String>) {
//    var input = mutableListOf<Byte>(
//            1, 2, 3, 4, 5, 6, 7, 8, 9, 0
//    )
    val bytes = ByteBuffer()
//    bytes.bytes = input

    bytes.writeBoolean(true)
    bytes.writeByte(-10)
    bytes.writeBytes(listOf(1, 2, -3, 4, 5))
    bytes.writeDouble(1.2)
    bytes.writeFloat(2.3f)
    bytes.writeInt(-456)
    bytes.writeShort(-789)
    bytes.writeUnsignedInt(1011U)
    bytes.writeUTF("テストメッセージ")
    bytes.writeUTFBytes("test message")

    bytes.position = 0

    println("boolean: " + bytes.readBoolean())
    println("byte: " + bytes.readByte())
    println("bytes: " + bytes.readBytes(5))
    println("double: " + bytes.readDouble())
    println("float: " + bytes.readFloat())
    println("int: " + bytes.readInt())
    println("short: " + bytes.readShort())
    println("uint: " + bytes.readUnsignedInt())
    println("utf-8: " + bytes.readUTF())
    println("utf-8: " + bytes.readUTFBytes(12))

}