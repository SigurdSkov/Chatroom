package rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import request.ChatroomRequest;
import service.ChatroomService;

@Path(value = "/")
public class ChatroomEndpoint {
    @Inject
    ChatroomService service;

    @Path("chatroom/")
    @POST
    public Response saveChatroom(ChatroomRequest request) {
        return service.saveChatroom(request);
    }

    @Path("chatroom/")
    @DELETE
    public Response removeChatroom(ChatroomRequest request) {
        return service.removeChatroom(request);
    }
}