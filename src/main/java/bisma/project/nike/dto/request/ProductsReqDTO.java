package bisma.project.nike.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsReqDTO {

    private String name;
    private String description;
    private String mainImg;
    private String price;


    @NotNull(message = "category tidak boleh null")
    private Long categoryId;


    @Override
    public String toString() {
        return "ProductsReqDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", mainImg='" + mainImg + '\'' +
                ", price='" + price + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
