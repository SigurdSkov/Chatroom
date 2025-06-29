package dao;

import Entities.MessageEntity;
import jakarta.inject.Inject;

import javax.persistence.EntityManager;
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
}
