package entities;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class ChatroomEntity {
    UUID chatroomId;
    String theme;
    List<UUID> users;
}
