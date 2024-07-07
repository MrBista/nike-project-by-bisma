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
    private String cover;




    @Override
    public String toString() {
        return "ProductResDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", mainImg='" + cover + '\'' +
                '}';
    }
}
