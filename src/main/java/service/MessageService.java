package service;

import entities.MessageEntity;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MessageDao;
import dto.MessageDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import request.MessageRequest;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class MessageService {

    MessageDao messageDao;

    public List<MessageDto> getMessageOnOpen(UUID chatroomId) {
        return messageDao.Startup(chatroomId).stream()
                .map(this::MessageEntityToDto)
                .collect(Collectors.toList());
    }

    public void SetMessage(MessageRequest body) {
        String filename = writeToFile(body);
        File file = new File(filename);
        if(file.length() > 1024*1024) {
            List<MessageEntity> messages = fromFileToMessages(file);
            messageDao.saveChatHistory(messages);
            if (!file.delete()) {
                Response.serverError().build();
                return;
            }
        }
        Response.ok().build();
    }

    private String writeToFile(MessageRequest body) {
        String filename;
        UUID id = body.getChatroomId();
        filename = String.format("file_%s.txt", id);
        MessageEntity entityToSave = messageRequestToEntity(body);

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonString = objectMapper.writeValueAsString(entityToSave);

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

    private MessageDto MessageEntityToDto(MessageEntity entity) {
        return new MessageDto(entity.getUserId(), entity.getMessage(), entity.getTime());
    }
    private MessageEntity messageRequestToEntity(MessageRequest body) {
        return new MessageEntity(body.getChatroomId(), body.getUser(), Timestamp.from(Instant.now()), body.getMessage());
    }
}