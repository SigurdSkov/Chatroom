package dto;

import java.sql.Timestamp;

public record MessageDto(String username, String text, Timestamp timestamp) {}
