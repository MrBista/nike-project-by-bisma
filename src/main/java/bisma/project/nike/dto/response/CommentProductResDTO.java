package bisma.project.nike.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentProductResDTO {
    private String comment;
    private Long productId;

    @Override
    public String toString() {
        return "CommentProductResDTO{" +
                "comment='" + comment + '\'' +
                ", productId=" + productId +
                '}';
    }
}
