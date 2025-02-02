package bisma.project.nike.dto.response;

import bisma.project.nike.model.CategorySub;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResDTO {
    private Long id;
    private String categoryName;
    private List<CategorySub> subs;

    @Override
    public String toString() {
        return "CategoryResDTO{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", subs=" + subs +
                '}';
    }
}
