package bisma.project.nike.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignInRequestDTO {
    @NotBlank(message = "email cannot be blank")
    @NotNull(message = "email cannot be empty")
    private String email;

    @NotBlank(message = "password cannot be empty")
    private String password;
}
