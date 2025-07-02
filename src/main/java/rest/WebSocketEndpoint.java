package rest;

import dto.ChatroomDto;
import dto.MessageDto;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.ws.rs.PathParam;
import request.MessageRequest;
import service.MessageService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{chatroomId}")
public class WebSocketEndpoint {
    @Inject
    MessageService service;

    private static final Map<UUID, Map<UUID, Session>> chatroomSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("chatroomId") String chatroomIdStr) {
        UUID chatroomId = UUID.fromString(chatroomIdStr);
        UUID userId = UUID.fromString(session.getRequestParameterMap().get("userId").getFirst());

        try {
            Map<UUID, Session> userSessions = chatroomSessions.computeIfAbsent(
                    chatroomId,
                    userSession -> new ConcurrentHashMap<>()
            );

            userSessions.put(userId, session);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        broadcastManyMessages(chatroomId, service.getMessageOnOpen(chatroomId));
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("chatroomId") String chatroomIdStr,
                          MessageRequest req) {
        try {
            UUID chatroomId = UUID.fromString(chatroomIdStr);
            UUID userId = req.getUser();

            switch (req.getMessageType()) {
                case NORMAL:
                    service.SetMessage(req);
                    broadcast(chatroomId, new MessageDto(userId, req.getMessage(), req.getTimestamp()));
                    break;
                case ADD_USER:
                    if (!userInChat(chatroomId, userId)) {
                        addUserToChatroom(session, chatroomId, userId);
                    }
                    break;
                case REMOVE_USER:
                    if (userInChat(chatroomId, userId)) {
                        removeUserFromChatroom(chatroomId, userId);
                    }
                    break;
                default:
                    throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnClose
    public void onClose(Session session, ChatroomDto chatroomDto) {
        try {
            UUID chatroomId = chatroomDto.chatroomId();

            chatroomSessions.getOrDefault(chatroomId, Collections.emptyMap())
                    .entrySet()
                    .removeIf(entry -> entry.getValue().equals(session));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void broadcastManyMessages(UUID chatroomId, List<MessageDto> messageDtos) {
        Optional.ofNullable(chatroomSessions.get(chatroomId))
                .ifPresent(uuidSessionMap -> uuidSessionMap
                        .values()
                        .stream()
                        .filter(Session::isOpen)
                        .forEach(specificSession ->
                                specificSession.getAsyncRemote().sendObject(messageDtos)));
    }

    private void broadcast(UUID chatroomId, MessageDto message) {
        Optional.ofNullable(chatroomSessions.get(chatroomId))
                .ifPresent(session -> session
                        .values()
                        .stream()
                        .filter(Session::isOpen)
                        .forEach(specificSession ->
                                specificSession.getAsyncRemote().sendObject(message)));
    }

    private void addUserToChatroom(Session session, UUID userId, UUID chatroomId) {
        if (session != null && session.isOpen()) {
            Map<UUID, Session> userSessions = chatroomSessions.computeIfAbsent(
                    chatroomId,
                    id -> new ConcurrentHashMap<>()
            );
            userSessions.put(userId, session);
        }
    }

    private void removeUserFromChatroom(UUID chatroomId, UUID userId) {
        if (chatroomSessions.containsKey(chatroomId)) {
            chatroomSessions.get(chatroomId).remove(userId);
        }
    }

    private boolean userInChat(UUID chatroomId, UUID userId) {
        return chatroomSessions.get(chatroomId).containsKey(userId);
    }
}
