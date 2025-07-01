package dto;

import java.sql.Timestamp;
import java.util.UUID;

public record MessageDto(UUID user, String text, Timestamp timestamp) {}
