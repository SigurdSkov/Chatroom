package dao;

import entities.UserEntity;
import jakarta.inject.Inject;
import request.UserRequest;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UserDao {
    @Inject
    EntityManager entityManager;

    public UserEntity getUser(UserRequest request) {
        try {
        return entityManager.createQuery("SELECT u FROM UserEntity u " +
                        "WHERE u.username = :username " +
                        "AND u.password = :password", UserEntity.class)
                .setParameter("username", request.getUsername())
                .setParameter("password", request.getPassword())
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user", e);
        }
    }

    public void saveUser(UserEntity entity) {
        entityManager.persist(entity);
    }

    public void removeUser(UserRequest request) {
        //entityManager.remove(user);
    }
}
