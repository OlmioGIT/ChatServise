data class Chat(
    val chatId: Int = 0,
    val senderName: String = "",
    val userTaker: String = "",
    val messages: MutableList<Message>
)