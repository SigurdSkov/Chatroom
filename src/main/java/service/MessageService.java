package service;

import Entities.MessageEntity;
import dao.ChatroomDao;
import dao.MessageDao;
import dto.MessageDto;
import dto.ChatroomDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import request.MessageRequest;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class MessageService {

    MessageDao messageDao;

    ChatroomDao chatroomDao;

    public Response GetMessage(MessageRequest body) {
        UUID chatroomId;
        try {
            chatroomId = body.chatroomId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<MessageDto> messageDtos = messageDao.Startup(chatroomId)
                .stream().map(this::MessageEntityToDto)
                .collect(Collectors.toList());

        String theme = chatroomDao.getTheme(chatroomId);

        messageDtos.sort(Comparator.comparing(MessageDto::timestamp).reversed());

        ChatroomDto chatroomDto = new ChatroomDto(messageDtos, theme);
        return Response.ok(chatroomDto).build();
    }

    /*public Response SetMessage(String userId, String text, UUID chatroomId) {
        //Gem som DAO

        //Smid på MQ
        //Når MQ fuld
        //Skriv det altsammen på en gang
    }*/
    private MessageDto MessageEntityToDto(MessageEntity entity) {
        return new MessageDto(entity.getUsername(), entity.getMessage(), entity.getTime());
    }
}