class ChatService {
    private val chats = mutableListOf<Chat>()
    private var logUserIdNow = ""

    class ChatOrMessageNotFoundException(message: String) : RuntimeException(message)

    fun logUserId(userId:String){
        logUserIdNow = userId
    }

    fun add(senderName:String, userTaker: String, thisMessage: String): Chat{
        val currentChat = chats.filter {(it.senderName == senderName && it.userTaker == userTaker || (it.senderName == userTaker && it.userTaker == senderName))  }

    if (currentChat.isNotEmpty())
        throw ChatOrMessageNotFoundException("Уже есть чат с отправителем = $senderName и получателем = $userTaker")

    val chatId = if (chats.isNotEmpty() && chats.last().chatId > 0) {
        chats.last().chatId + 1
    }else{
        1
    }
        val chat = Chat(
            chatId, senderName, userTaker, mutableListOf()
        )
        chats += chat
        if (!thisMessage.equals(null) && thisMessage != "") {
            addMessage(chatId, senderName, thisMessage)
        }
        return chat
    }


    fun addMessage(chatId: Int, addressee: String, text: String): Message {
        val chat = getChatById(chatId)
        val messageId = if (chat.messages.isNotEmpty() && chat.messages.last().messageId > 0) {
            chat.messages.last().messageId + 1
        } else {
            1
        }
        val message = Message(messageId, chatId, addressee, text, false)
        chat.messages += message
        return message
    }

    private fun getChatById(chatId: Int): Chat {
        val chat = chats.find {
            it.chatId == chatId
        }
        return chat
            ?: throw ChatOrMessageNotFoundException("Чат с id = $chatId не найден")
    }

    fun delete(chatId: Int): Boolean {
        val chat = chats.find {
            it.chatId == chatId
        }
            ?: throw ChatOrMessageNotFoundException("Чат с id = $chatId не найден")
        chat.apply {
            chats.removeIf {
                it.chatId == chatId
            }
            return true
        }
    }


    fun deleteMessage(chatId: Int, messageId: Int): Boolean {
        val chat = chats.find {
            it.chatId == chatId
        }
            ?: throw ChatOrMessageNotFoundException("Чат с id = $chatId не найден")
        chat.messages.find {
            it.messageId == messageId
        }
            ?: throw ChatOrMessageNotFoundException("Сообщение с id = $messageId не найдено")
        chat.apply {
            this.messages.removeIf {
                it.messageId == messageId
            }
            if (chat.messages.isEmpty())
                chats.remove(chat)
            return true
        }
    }

    fun getChatInfo(chat: Chat): String {
        return "Chat id: ${chat.chatId}; with ${
            if (chat.userTaker.equals(logUserIdNow, true)) chat.userTaker else chat.senderName
        }; last message: <${chat.messages.lastOrNull() ?: "сообщений нет"}>"
    }

    fun getMessages(chatId: Int, lastMessageId: Int, count: Int): List<Message> {
        return chats.singleOrNull() { it.chatId == chatId }
            .let { it?.messages ?: throw ChatOrMessageNotFoundException("Чат с id = $chatId не найден") }
            .asSequence()
            .drop(lastMessageId)
            .take(count)
            .ifEmpty { throw ChatOrMessageNotFoundException("Сообщение с id = $lastMessageId не найдено") }
            .onEach { it.ifLook = true }
            .toList()
    }

    fun getUnreadChatsCount(): List<Chat> {
        return chats.filter {
            it.messages.isNotEmpty()
        }.filter { it.messages.any() }.filter { it ->
            it.messages.any {
                !it.ifLook
            }
        }
    }


    private fun getMessageByMessageId(chatId: Int, messageId: Int): Message? =
        chats.find {
            it.chatId == chatId
        }?.messages?.find {
            it.messageId == messageId
        }
}
