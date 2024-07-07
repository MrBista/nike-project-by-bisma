package bisma.project.nike.dto.entity;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategorySubEntityDTO {
    private Long id;
    private String name;
    private String description;
    private long createdAt;
    public record Category(Long id, String name){
    }
    CategorySubEntityDTO.Category category;
}
