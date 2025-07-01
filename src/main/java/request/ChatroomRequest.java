package request;

import java.util.List;
import java.util.UUID;

public record ChatroomRequest(
        UUID chatroomId,
        List<UUID> userIds,
        String theme
) {}
