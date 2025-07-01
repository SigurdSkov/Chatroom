package rest;
/*
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import request.MessageRequest;
import request.ChatroomRequest;
import service.ChatroomService;

@Path(value = "/")
public class MessageEndpoint {
    @Inject
    ChatroomService service;

    @Path("chatroom/")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetMessage(ChatroomRequest body) {
        return service.GetMessage(body);
    }

    @Path("chatroom/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response SetMessage(MessageRequest body) {
        return service.SetMessage(body);
    }
}*/