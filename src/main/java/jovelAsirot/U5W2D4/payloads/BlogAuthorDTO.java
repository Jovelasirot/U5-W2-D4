package jovelAsirot.U5W2D4.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BlogAuthorDTO(@NotEmpty(message = "The name is required")
                            @Size(min = 2, message = "The name can't be less than two characters")
                            String name,
                            @NotEmpty(message = "The surname is required")
                            @Size(min = 2, message = "The surname can't be less than two characters")
                            String surname,
                            @NotEmpty(message = "The email is required")
                            @Email(message = "The email given is invalid")
                            String email,
                            @NotEmpty(message = "The birth date is required")
                            String birthDate) {
}
