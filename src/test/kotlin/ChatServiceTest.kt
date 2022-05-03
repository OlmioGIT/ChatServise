import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertEquals



class ChatServiceTest {

        @Test
        fun add() {
            val service = ChatService()
            service.add("Alex", "Petya", "Initial first chat")
            val result = service.add("Petya", "Serg", "Initial second chat")
            service.add("Oleg", "Tanya", "Initial third chat")

            service.addMessage(1, "Petya", "First Msg")
            service.addMessage(1, "Petya", "Second Msg")
            service.addMessage(2, "Serg", "First Msg")
            service.addMessage(2, "Serg", "Second Msg")
            service.addMessage(3, "Tanya", "First Msg")
            service.addMessage(3, "Tanya", "Second Msg")
            service.addMessage(3, "Tanya", "Third Msg")
            service.addMessage(3, "Tanya", "Fourth Msg")

            val expected = Chat(
                2, "Petya", "Serg", mutableListOf(
                    Message(1, 2, "Petya", "Initial second chat", false),
                    Message(2, 2, "Serg", "First Msg", false),
                    Message(3, 2, "Serg", "Second Msg", false)
                )
            )
            assertEquals(expected, result)
        }

        @Test
        fun addMessage() {
            val service = ChatService()
            service.add("Alex", "Petya", "Initial first chat")
            service.add("Petya", "Serg", "Initial second chat")
            service.add("Oleg", "Tanya", "Initial third chat")

            service.addMessage(1, "Petya", "First Msg")
            service.addMessage(1, "Petya", "Second Msg")
            service.addMessage(2, "Serg", "First Msg")
            service.addMessage(2, "Serg", "Second Msg")
            service.addMessage(3, "Tanya", "First Msg")
            service.addMessage(3, "Tanya", "Second Msg")
            service.addMessage(3, "Tanya", "Third Msg")
            service.addMessage(3, "Tanya", "Fourth Msg")

            val result = service.addMessage(3, "Tanya", "Fifth Msg")
            val expected = Message(6, 3, "Tanya", "Fifth Msg", false)
            assertEquals(expected, result)
        }

        @Test
        fun delete() {
            val service = ChatService()
            service.add("Alex", "Petya", "Initial first chat")
            service.add("Petya", "Serg", "Initial second chat")
            service.add("Oleg", "Tanya", "Initial third chat")

            service.addMessage(1, "Petya", "First Msg")
            service.addMessage(1, "Petya", "Second Msg")
            service.addMessage(2, "Serg", "First Msg")
            service.addMessage(2, "Serg", "Second Msg")
            service.addMessage(3, "Tanya", "First Msg")
            service.addMessage(3, "Tanya", "Second Msg")
            service.addMessage(3, "Tanya", "Third Msg")
            service.addMessage(3, "Tanya", "Fourth Msg")

            val result = service.delete(2)
            assertTrue(result)
        }

        @Test(expected = ChatService.ChatOrMessageNotFoundException::class)
        fun deleteShouldThrow() {
            val service = ChatService()
            service.add("Alex", "Petya", "Initial first chat")
            service.add("Petya", "Serg", "Initial second chat")
            service.add("Oleg", "Tanya", "Initial third chat")

            service.addMessage(1, "Petya", "First Msg")
            service.addMessage(1, "Petya", "Second Msg")
            service.addMessage(2, "Serg", "First Msg")
            service.addMessage(2, "Serg", "Second Msg")
            service.addMessage(3, "Tanya", "First Msg")
            service.addMessage(3, "Tanya", "Second Msg")
            service.addMessage(3, "Tanya", "Third Msg")
            service.addMessage(3, "Tanya", "Fourth Msg")

            val result = service.delete(10)
            assertFalse(result)
        }

        @Test
        fun deleteMessage() {
            val service = ChatService()
            service.add("Alex", "Petya", "Initial first chat")
            service.add("Petya", "Serg", "Initial second chat")
            service.add("Oleg", "Tanya", "Initial third chat")

            service.addMessage(1, "Petya", "First Msg")
            service.addMessage(1, "Petya", "Second Msg")
            service.addMessage(2, "Serg", "First Msg")
            service.addMessage(2, "Serg", "Second Msg")
            service.addMessage(3, "Tanya", "First Msg")
            service.addMessage(3, "Tanya", "Second Msg")
            service.addMessage(3, "Tanya", "Third Msg")
            service.addMessage(3, "Tanya", "Fourth Msg")

            service.deleteMessage(1, 1)
            service.deleteMessage(1, 2)
            val result = service.deleteMessage(1, 3)
            assertTrue(result)
        }

        @Test(expected = ChatService.ChatOrMessageNotFoundException::class)
        fun deleteMessageShouldThrow() {
            val service = ChatService()
            service.add("Alex", "Petya", "Initial first chat")
            service.add("Petya", "Serg", "Initial second chat")
            service.add("Oleg", "Tanya", "Initial third chat")

            service.addMessage(1, "Petya", "First Msg")
            service.addMessage(1, "Petya", "Second Msg")
            service.addMessage(2, "Serg", "First Msg")
            service.addMessage(2, "Serg", "Second Msg")
            service.addMessage(3, "Tanya", "First Msg")
            service.addMessage(3, "Tanya", "Second Msg")
            service.addMessage(3, "Tanya", "Third Msg")
            service.addMessage(3, "Tanya", "Fourth Msg")

            val result = service.deleteMessage(1, 7)
            assertFalse(result)
        }

        @Test
        fun getChatInfo() {
            val service = ChatService()
            service.logUserId("Tanya")
            service.add("Alex", "Petya", "Initial first chat")
            service.add("Petya", "Serg", "Initial second chat")
            val chat = service.add("Oleg", "Tanya", "Initial third chat")

            service.addMessage(1, "Petya", "First Msg")
            service.addMessage(1, "Petya", "Second Msg")
            service.addMessage(2, "Serg", "First Msg")
            service.addMessage(2, "Serg", "Second Msg")
            service.addMessage(3, "Tanya", "First Msg")
            service.addMessage(3, "Tanya", "Second Msg")
            service.addMessage(3, "Tanya", "Third Msg")
            service.addMessage(3, "Tanya", "Fourth Msg")

            val expected = "Chat id: 3; with Tanya; last message: <Message(messageId=5, chatId=3, addressee=Tanya, text=Fourth Msg, ifLook=false)>"
            val result = service.getChatInfo(chat)
            assertEquals(expected, result)
        }

        @Test
        fun getMessages() {
            val service = ChatService()
            service.logUserId("Tanya")
            service.add("Alex", "Petya", "Initial first chat")
            service.add("Petya", "Serg", "Initial second chat")
            service.add("Oleg", "Tanya", "Initial third chat")

            service.addMessage(1, "Petya", "First Msg")
            service.addMessage(1, "Petya", "Second Msg")
            service.addMessage(2, "Serg", "First Msg")
            service.addMessage(2, "Serg", "Second Msg")
            service.addMessage(3, "Tanya", "First Msg")
            service.addMessage(3, "Tanya", "Second Msg")
            service.addMessage(3, "Tanya", "Third Msg")
            service.addMessage(3, "Tanya", "Fourth Msg")
            service.addMessage(3, "Tanya", "Fifth Msg")

            val expected = listOf<Message>(
                Message(messageId=4, chatId=3, addressee="Tanya", text="Third Msg", ifLook=true),
                Message(messageId=5, chatId=3, addressee="Tanya", text="Fourth Msg", ifLook=true),
                Message(messageId=6, chatId=3, addressee="Tanya", text="Fifth Msg", ifLook=true)
            )
            val result = service.getMessages(3,3,5)
            assertEquals(expected, result)
        }

        @Test(expected  = ChatService.ChatOrMessageNotFoundException::class)
        fun getMessagesShouldThrow() {
            val service = ChatService()
            service.logUserId("Tanya")
            service.add("Alex", "Petya", "Initial first chat")
            service.add("Petya", "Serg", "Initial second chat")
            service.add("Oleg", "Tanya", "Initial third chat")

            service.addMessage(1, "Petya", "First Msg")
            service.addMessage(1, "Petya", "Second Msg")
            service.addMessage(2, "Serg", "First Msg")
            service.addMessage(2, "Serg", "Second Msg")
            service.addMessage(3, "Tanya", "First Msg")
            service.addMessage(3, "Tanya", "Second Msg")
            service.addMessage(3, "Tanya", "Third Msg")
            service.addMessage(3, "Tanya", "Fourth Msg")
            service.addMessage(3, "Tanya", "Fifth Msg")

            val expected = listOf<Message>(
                Message(messageId=4, chatId=3, addressee="Tanya", text="Third Msg", ifLook=true),
                Message(messageId=5, chatId=3, addressee="Tanya", text="Fourth Msg", ifLook=true),
                Message(messageId=6, chatId=3, addressee="Tanya", text="Fifth Msg", ifLook=true)
            )
            val result = service.getMessages(3,47,5)
            assertEquals(expected, result)
        }

        @Test
        fun getUnreadChatsCount() {
            val service = ChatService()
            service.add("Alex", "Petya", "Initial first chat")
            service.add("Petya", "Serg", "Initial second chat")
            service.add("Oleg", "Tanya", "Initial third chat")

            service.addMessage(1, "Petya", "First Msg")
            service.addMessage(1, "Petya", "Second Msg")
            service.addMessage(2, "Serg", "First Msg")
            service.addMessage(2, "Serg", "Second Msg")
            service.addMessage(3, "Tanya", "First Msg")
            service.addMessage(3, "Tanya", "Second Msg")
            service.addMessage(3, "Tanya", "Third Msg")
            service.addMessage(3, "Tanya", "Fourth Msg")
            service.addMessage(3, "Tanya", "Fifth Msg")

            service.logUserId("Petya")
            service.getMessages(1, 2, 5)
            service.delete(2)
            service.logUserId("Tanya")
            service.getMessages(3, 3, 5)

            val expected = mutableListOf<Chat>(
                Chat(chatId = 1, senderName = "Alex", userTaker = "Petya", mutableListOf(
                    Message(messageId = 1, chatId = 1, addressee = "Alex", text = "Initial first chat", ifLook = false),
                    Message(messageId = 2, chatId = 1, addressee = "Petya", text = "First Msg", ifLook = false),
                    Message(messageId = 3, chatId = 1, addressee = "Petya", text = "Second Msg", ifLook = true)
                )
                ),
                Chat(chatId = 3, senderName = "Oleg", userTaker = "Tanya",
                    mutableListOf(
                        Message(messageId = 1, chatId = 3, addressee = "Oleg", text = "Initial third chat", ifLook = false),
                        Message(messageId = 2, chatId = 3, addressee = "Tanya", text = "First Msg", ifLook = false),
                        Message(messageId = 3, chatId = 3, addressee = "Tanya", text = "Second Msg", ifLook = false),
                        Message(messageId = 4, chatId = 3, addressee = "Tanya", text = "Third Msg", ifLook = true),
                        Message(messageId = 5, chatId = 3, addressee = "Tanya", text = "Fourth Msg", ifLook = true),
                        Message(messageId = 6, chatId = 3, addressee = "Tanya", text = "Fifth Msg", ifLook = true)
                    )
                )
            )
            val result = service.getUnreadChatsCount()
            assertEquals(expected, result)
        }
    }