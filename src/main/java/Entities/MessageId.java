package Entities;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class MessageId implements Serializable {
    private UUID chatroomId;
    private UUID userId;
}
