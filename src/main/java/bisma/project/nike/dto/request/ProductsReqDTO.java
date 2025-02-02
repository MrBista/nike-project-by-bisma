package bisma.project.nike.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsReqDTO {

    private String name;
    private String description;
    private String price;
    private String summary;
    private String cover;

}
