package dto;

import java.util.List;

public record ChatroomDto(List<MessageDto> messageDtos, String theme) {}