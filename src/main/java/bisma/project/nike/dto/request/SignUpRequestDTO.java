package bisma.project.nike.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpRequestDTO {

    @NotBlank(message = "username tidak boleh kosong")
    @NotNull(message = "username tidak boleh null")
    @Size(min = 5, max = 60, message = "username minimal 5 karakter dan maksimal 30 karaketer")
    private String username;

    @Email(message = "Invalid email")
    @NotBlank(message = "email tidak boleh kosong")
    @NotNull(message = "email tidak boleh null")
    @Size(min = 5, max = 30, message = "username minimal 5 karakter dan maksimal 30 karaketer")
    private String email;

    private String firstName;

    private String lastName;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;

    private List<String> roles;


}
