package request;

import java.util.List;
import java.util.UUID;

public record OpenChatroomRequest(
        UUID chatroomId,
        List<UUID> userIds
) {}
