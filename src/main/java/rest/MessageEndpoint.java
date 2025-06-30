package rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import request.MessageRequest;
import request.OpenChatroomRequest;
import service.MessageService;

@Path("/")
public class MessageEndpoint {
    @Inject
    MessageService service;

    @Path("messages")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetMessage(OpenChatroomRequest body) {
        return service.GetMessage(body);
    }

    @Path("messages")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response SetMessage(MessageRequest body) {
        return service.SetMessage(body);
    }
}

