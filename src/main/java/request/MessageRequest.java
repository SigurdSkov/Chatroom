package request;

import domain.UserHandling;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
public class MessageRequest {
    UUID chatroomId;
    UUID user;
    String message;
    Timestamp timestamp;
    UserHandling.Method messageType;
}
