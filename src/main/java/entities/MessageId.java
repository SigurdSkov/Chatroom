package entities;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class MessageId implements Serializable {
    private UUID chatroomId;
    private UUID userId;
    private Timestamp timestamp;
}
