package bisma.project.nike.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentProductEntityDTO {
    private Long id;
    private String comment;
    private String createdBy;
    public record ProductComment(Long id){
    }

    ProductComment product;

    private Long userId;


}
