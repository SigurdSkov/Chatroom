package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@IdClass(MessageId.class)
@Getter
@AllArgsConstructor
public class MessageEntity {
    @Id
    private UUID chatroomId;
    @Id
    private UUID userId;
    @Id
    private Timestamp time;
    private String message;
}
