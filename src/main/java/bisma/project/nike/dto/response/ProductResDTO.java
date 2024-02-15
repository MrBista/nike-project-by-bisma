package bisma.project.nike.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResDTO {
    private Long id;
    private String name;
    private String description;
    private String price;
    private String mainImg;
    private Long categoryId;


    @Override
    public String toString() {
        return "ProductResDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", mainImg='" + mainImg + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
