package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    //3. Create New Message
    public Message createMessage(Message message) throws Exception{
        if(message.getMessageText().isBlank()){
            throw new Exception("Message is blank");
        }else if( message.getMessageText().length() > 255){
            throw new Exception("Message is too long");
        }else if(accountRepository.findById(message.getPostedBy()) == null){
            throw new Exception("User does not exist");
        }
        else{
            return messageRepository.save(message);
        }
    }

    //4. Retrieve All Messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    //5. Retrieve Message by Id
    public Message getMessageById(Integer messageId){
        return messageRepository.findById(messageId).orElse(null);
    }

    //6. Delete Message by ID
    public int deleteMessageById(Integer messageId){

        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return 1;
        }else 
            return 0;
    }

    //7. Update Message Text by ID
    public int updateMessageByMessageID(Integer messageId, String newMessageText){
        if(newMessageText == null || newMessageText.isBlank() || newMessageText.length() > 255)
            return 0;

        Message foundMessage = messageRepository.findById(messageId).orElse(null);
        
        if(foundMessage == null)
            return 0;

        foundMessage.setMessageText(newMessageText);
        messageRepository.save(foundMessage);
        return 1;
    }

    //8. Retrieve All Messages by Specified User
    public List<Message> getAllMessagesByPostedBy(Integer postedBy){
        return messageRepository.findByPostedBy(postedBy);
    }
}