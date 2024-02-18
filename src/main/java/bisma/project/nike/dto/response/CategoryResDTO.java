package bisma.project.nike.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResDTO {
    private Long id;
    private String categoryName;

    @Override
    public String toString() {
        return "CategoryResDTO{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
