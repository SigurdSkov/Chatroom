package service;

import Entities.MessageEntity;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ChatroomDao;
import dao.MessageDao;
import dto.MessageDto;
import dto.ChatroomDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import request.MessageRequest;
import request.OpenChatroomRequest;

import java.io.*;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class MessageService {

    MessageDao messageDao;

    ChatroomDao chatroomDao;

    public Response GetMessage(OpenChatroomRequest body) {
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

        //Send til participants? Hvordan gør jeg det?
        return Response.ok(chatroomDto).build();
    }

    public Response SetMessage(MessageRequest body) {
        String filename = writeToFile(body);
        File file = new File(filename);
        if(file.length() > 1024*1024) {
            List<MessageEntity> messages = fromFileToMessages(file);
            messageDao.saveChatHistory(messages);
            if (!file.delete()) {
                return Response.serverError().build();
            }
        }

        return Response.ok().build();
    }
    private MessageDto MessageEntityToDto(MessageEntity entity) {
        return new MessageDto(entity.getUserId(), entity.getChatroomId(), entity.getMessage(), entity.getTime());
    }
    private MessageEntity bodyToEntity(MessageRequest body) {
        return new MessageEntity(body.getChatroomId(), body.getUser(), Timestamp.from(Instant.now()), body.getMessage());
    }
    private String writeToFile(MessageRequest body) {
        String filename;
        UUID id = body.getChatroomId();
        filename = String.format("file_%s.txt", id);
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonString = objectMapper.writeValueAsString(body);

            FileWriter myWriter = new FileWriter(filename);

            myWriter.write(jsonString);
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filename;
    }

    private List<MessageEntity> fromFileToMessages(File file) {
        List<MessageEntity> messages = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory jsonFactory = objectMapper.getFactory();

        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
            JsonParser parser = jsonFactory.createParser(inputStream);
            while (parser.nextToken() != null) {
                MessageEntity entity = objectMapper.readValue(parser, MessageEntity.class);
                messages.add(entity);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read messages from file: " + file.getName(), e);
        }

        return messages;
    }
}