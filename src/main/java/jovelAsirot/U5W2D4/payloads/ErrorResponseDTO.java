package jovelAsirot.U5W2D4.payloads;

import java.time.LocalDateTime;

public record ErrorResponseDTO(String message, LocalDateTime timestamp) {
}
