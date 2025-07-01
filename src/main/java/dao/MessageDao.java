package dao;

import entities.MessageEntity;
import jakarta.inject.Inject;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class MessageDao {
    @Inject
    EntityManager entityManager;

    public List<MessageEntity> Startup(UUID chatroomId) {
        return entityManager.createQuery(
                        "SELECT * FROM Message m " +
                                "WHERE m.chatroomId = :chatroomId " +
                                "AND m.timestamp > :cutoff", MessageEntity.class)
                .setParameter("chatroomId", chatroomId)
                .setParameter("cutoff", Instant.now().minus(365, ChronoUnit.DAYS))
                .setMaxResults(255)
                .getResultList();
    }

    @Transactional
    public void saveChatHistory(List<MessageEntity> history) {
        history.forEach(messageEntity -> entityManager.persist(messageEntity));
        entityManager.persist(history);
    }
}
