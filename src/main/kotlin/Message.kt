data class Message(
    var messageId: Int = 0,
    val chatId: Int = 0,
    val addressee: String ="",
    val text:String,
    var ifLook: Boolean = false
    )
