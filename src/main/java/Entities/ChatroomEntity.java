package Entities;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class ChatroomEntity {
    UUID chatroomId;
    String theme;
}
