package bisma.project.nike.dto.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductEntityDTO {
    private Long id;
    private String name;
    private String description;
    private String price;
    private String mainImg;
    private String status;
    private String createdBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    public record Comment(Long id, String comment, String createdBy) {
    }
    private List<Comment> comments;

    @Override
    public String toString() {
        return "ProductEntityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", mainImg='" + mainImg + '\'' +
                ", status='" + status + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", comments=" + comments +
                '}';
    }
}

