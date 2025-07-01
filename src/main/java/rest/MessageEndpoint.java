package rest;

/*import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import request.MessageRequest;
import request.ChatroomRequest;
import service.MessageService;

@Path(value = "/chat")
public class MessageEndpoint {
    @Inject
    MessageService service;

    @Path("messages")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetMessage(ChatroomRequest body) {
        return service.GetMessage(body);
    }

    @Path("messages")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response SetMessage(MessageRequest body) {
        return service.SetMessage(body);
    }
}

*/