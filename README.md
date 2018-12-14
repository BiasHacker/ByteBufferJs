# ByteBufferJs
as3のByteArrayみたいなもの (Kotlin/JS)

### Example
```JavaScript
var bytes = new ByteBufferJs.ByteBuffer();
bytes.writeUTF("message test");
console.log(bytes.toString());
```
