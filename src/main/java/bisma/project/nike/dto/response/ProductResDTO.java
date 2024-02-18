package bisma.project.nike.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
