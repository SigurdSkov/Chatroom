package Entities;

import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@IdClass(MessageId.class)
@Getter
public class MessageEntity {
    @Id
    private UUID chatroomId;
    @Id
    private UUID userId;

    private Timestamp time;
    private String message;
    private String username;
}
