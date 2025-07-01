package service;

import dao.UserDao;
import entities.UserEntity;
import jakarta.ws.rs.core.Response;
import request.UserRequest;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public class UserService {
    UserDao userDao;

    public Response getUser(UserRequest request) {
        return Response.ok(userDao.getUser(request)).build();
    }

    public Response saveUser(UserRequest request) {
        userDao.saveUser(fromRequestToEntity(request));
        return Response.ok().build();
    }

    public Response removeUser(UserRequest request) {
        userDao.removeUser(request);
        return Response.ok().build();
    }

    public UserEntity fromRequestToEntity(UserRequest request) {
        return new UserEntity(UUID.randomUUID(), request.getUsername(), request.getPassword());
    }
}
