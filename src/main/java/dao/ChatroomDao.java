package dao;

import entities.ChatroomEntity;
import jakarta.inject.Inject;

import javax.persistence.EntityManager;
import java.util.UUID;

public class ChatroomDao {
    @Inject
    EntityManager entityManager;

    public String getTheme(UUID chatroomId) {
        return entityManager.createQuery("SELECT c.theme FROM ChatroomEntity c " +
                "WHERE c.chatroomId = :chatroomId", String.class)
                .setParameter("chatroomId", chatroomId)
                .getSingleResult();
    }

    public void setChatroom(ChatroomEntity entity) {
        entityManager.persist(entity);
    }

    public void removeChatroom(ChatroomEntity entity) {
        entityManager.remove(entity);
    }
}
