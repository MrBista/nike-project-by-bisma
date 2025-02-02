package bisma.project.nike.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryReqDTO {

    @NotNull(message = "name category cannot be null")
    @NotBlank(message = "name category cannot be blank")
    private String name;

    private String description;

//    @NotEmpty(message = "subcategories cannot be empty")
//    List<@Valid CategorySubReqDTO> subCategories;

}
