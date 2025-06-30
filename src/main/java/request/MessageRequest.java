package request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class MessageRequest {
    UUID chatroomId;
    UUID user;
    String message;
}
