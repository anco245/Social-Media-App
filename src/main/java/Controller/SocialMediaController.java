package Controller;

import java.util.ArrayList;
import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {    

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //Creates endpoint on POST localhost:8080/register to create a new Account
        app.post("/register", this::postNewAccountHandler);

        //Creates endpoint on POST localhost:8080/login to verify my login
        app.post("/login", this::verfiyAccountHandler);

        //Creates endpoint on POST localhost:8080/messages to create a new message
        app.post("/messages", this::postNewMessageHandler);

        //Creates endpoint on GET localhost:8080/messages to retrieve all messages
        app.get("/messages", this::getAllMessagesHandler);

        //Creates an endpoint on GET localhost:8080/messages/{message_id} to retreive a message by ID
        app.get("/messages/{message_id}", this::getMessageByIdHandler);

        //Creates an endpoint on DELETE localhost:8080/messages/{message_id} to delete a message by ID
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);

        //Creates an endpoint on PATCH localhost:8080/messages/{message_id} to update a message by ID
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);

        //Creates an endpoint on GET localhost:8080/accounts/{account_id}/messages to retrieve all messages
        //by a particular user
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);

        return app;
    }
    
    /**
     * 1 - Handler to post a new user.
     * @param ctx 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postNewAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        if(!account.getUsername().equals("") && 
            account.password.length() >= 4 && 
            !accountService.usernameExists(account.username)){

            Account newAccount = accountService.addAccount(account);
            ctx.json(mapper.writeValueAsString(newAccount));
        } else {
            ctx.status(400);
        }
    }

    /**
     * 2 - Handler to verify login. If true, needs to give a jason representation of the verified
     * account: account id, username, and password
     * @param ctx 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void verfiyAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account accountToBeVerified = accountService.verifyAccount(account);

        if(accountToBeVerified != null)
        {
            ctx.json(mapper.writeValueAsString(accountToBeVerified));
        } else {
            ctx.status(401);
        }
    }

    /**
     * 3 - Handler to create a new message
     * @param ctx 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postNewMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        if(message.message_text.length() > 0 && message.message_text.length() < 255 && 
                accountService.getUserById(message.posted_by) != null) {
            Message newMessage = messageService.addMessage(message);
                
            ctx.json(mapper.writeValueAsString(newMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * 4 - Handler to get all messages
     * @param ctx 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();

        ctx.json(messages);
    }

    /**
     * 5 - Handler to get a message by its ID
     * @param ctx 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(id);

        if(message != null)
        {
            ctx.json(message);
        } else {
            ctx.json("");
        }
    }

    /**
     * 6 - Handler to delete a messages by its ID
     * @param ctx 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageById(id);

        if(message != null)
        {
            ctx.json(message);
        } else {
            ctx.json("");
        }
    }

    /**
     * 7 - Handler to update a message
     * @param ctx 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        message.setMessage_id(id);

        if(messageService.messageIdExists(id) && message.message_text.length() > 0 && 
                    message.message_text.length() < 255)
        {
            Message newMessage = messageService.updateMessage(message);

            ctx.json(mapper.writeValueAsString(newMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * 8 - Handler to get all messages by a specific user
     * @param ctx 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void getMessagesByUserHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("account_id"));

        //make sure id actually exists first
        ArrayList<Message> messageList = messageService.getMessagesByUser(id);

        if(messageList != null)
        {
            ctx.json(messageList);
        } else {
            ctx.json("");
        }
    }

}