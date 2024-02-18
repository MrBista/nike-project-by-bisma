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
    Long id;
    String comment;
    String createdBy;
    ProductEntityDTO products;
}
