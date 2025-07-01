package rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import request.UserRequest;
import service.UserService;

@Path(value = "/")
public class UserEndpoint {
    @Inject
    UserService service;

    @Path("user/")
    @GET
    public Response getUser(UserRequest request) {
        return service.getUser(request);
    }

    @Path("user/")
    @POST
    public Response saveUser(UserRequest request) {
        return service.saveUser(request);
    }

    @Path("user/")
    @DELETE
    public Response removeUser(UserRequest request) {
        return service.removeUser(request);
    }
}
