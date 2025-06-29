package rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import request.MessageRequest;
import service.MessageService;

@Path("/")
public class MessageEndpoint {
    @Inject
    MessageService service;

    @Path("messages")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetMessage(MessageRequest body) {
        return service.GetMessage(body);
    }
}

