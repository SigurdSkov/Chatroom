package entities;

import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;

@Entity
public class ChatroomEntity {
    UUID chatroomId;
    String theme;
    List<MessageEntity> messages;
}
