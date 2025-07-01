package service;

import dao.ChatroomDao;
import entities.ChatroomEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import request.ChatroomRequest;

import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ChatroomService {
    ChatroomDao chatroomDao;

    public Response saveChatroom(ChatroomRequest request) {
        chatroomDao.setChatroom(requestToEntity(request));
        return Response.ok().build();
    }

    public Response removeChatroom(ChatroomRequest request) {
        chatroomDao.removeChatroom(requestToEntity(request));
        return Response.ok().build();
    }

    private ChatroomEntity requestToEntity(ChatroomRequest request) {
        return new ChatroomEntity(request.chatroomId(), request.theme(), request.userIds());
    }
}
