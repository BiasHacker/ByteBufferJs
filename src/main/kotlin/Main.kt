import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import utils.toUTF8Bytes
import kotlin.browser.document

@JsName("main")
fun main(args: Array<String>) {
    var input = mutableListOf<Byte>(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 0
    )
    val bytes = ByteBuffer()
    bytes.bytes = input
    println(bytes.readBoolean())
}