package Service;

import Model.Message;
import DAO.MessagesDAO;

import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private MessagesDAO messagesDao;

    // no-args constructor for creating a new MessageService with a new MessagesDAO.
    public MessageService(){
        messagesDao = new MessagesDAO();
    }

    /**
     * Constructor for a MessageService when a MessageDAO is provided.
     * This is used for when a mock MessageDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of MessageService independently of MessageDAO.
     * @param MessageDAO
     */
    public MessageService(MessagesDAO messagesDao){
        this.messagesDao = messagesDao;
    }

    /**
     * Uses the MessagesDAO to retrieve all messages.
     *
     * @return all messages
     */
    public List<Message> getAllMessages() {
        return messagesDao.getAllMessages();
    }
    
    /**
     * Uses the MessageDAO to persist a Message.
     *
     * @param Message an Message object.
     * @return The persisted Message if the persistence is successful.
     */
    public Message addMessage(Message message) {
        return messagesDao.insertMessage(message);
    }

    /**
     * Uses the MessageDAO to retrieve an message by its id
     *
     * @param id of message
     * @return message associated with id
     */
    public Message getMessageById(int id) {
        return messagesDao.getMessageById(id);
    }

    /**
     * Uses the MessageDAO to delete a message by its id.
     *
     * @param id
     * @return Message that was deleted
     */
    public Message deleteMessageById(int id) {
        return messagesDao.deleteMessageById(id);
    }

    /**
     * Uses the MessageDAO to update a message.
     *
     * @param message object with text and id
     * @return Fully updated message
     */
    public Message updateMessage(Message message)
    {
        return messagesDao.updateMessage(message);
    }

    /**
     * Uses the MessageDAO to check if a message id exists in the message table
     *
     * @param message id to be checked
     * @return true if it exists, false otherwise
     */
    public boolean messageIdExists(int id)
    {
        return messagesDao.messageIdExists(id);
    }

    /**
     * Uses the MessageDAO retrieve all messages by a user
     *
     * @param posted_by value
     * @return An array list of messages belonging to the given user
     */
    public ArrayList<Message> getMessagesByUser(int id)
    {
        return messagesDao.getMessagesByUser(id);
    }
}
