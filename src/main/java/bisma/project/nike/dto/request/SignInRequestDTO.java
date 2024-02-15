package bisma.project.nike.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignInRequestDTO {
    @NotBlank(message = "email tidak boleh kosong")
    private String email;

    @NotBlank(message = "username tidak boleh kosong")
    private String username;

    @NotBlank(message = "password tidak boleh kosong")
    private String password;
}
