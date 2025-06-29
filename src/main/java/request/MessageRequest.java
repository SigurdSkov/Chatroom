package request;

import java.util.List;
import java.util.UUID;

public record MessageRequest(
        UUID chatroomId,
        List<UUID> userIds
) {}
