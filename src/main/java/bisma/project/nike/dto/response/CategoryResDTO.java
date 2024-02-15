package bisma.project.nike.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
