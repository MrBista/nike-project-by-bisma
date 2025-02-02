package bisma.project.nike.dto.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoryEntityDTO {
    private Long id;
    private String name;
    private String description;
    private long createdAt;

    private long updatedAt;

//    public record SubCategory(Long id, String name,String description ) {
//    }

//    List<SubCategory> subCategories;
}
