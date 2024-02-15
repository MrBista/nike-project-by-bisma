package bisma.project.nike.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReqDTO {

    @NotNull(message = "nama kategori tidak boleh null")
    @NotBlank(message = "nama kategori tidak boleh kosong")
    private String nameCategory;

    @Override
    public String toString() {
        return "CategoryReqDTO{" +
                "name='" + nameCategory + '\'' +
                '}';
    }
}
