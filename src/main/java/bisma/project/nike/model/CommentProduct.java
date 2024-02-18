package bisma.project.nike.model;

import bisma.project.nike.dto.entity.CommentProductEntityDTO;
import bisma.project.nike.dto.entity.ProductEntityDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@Table(name = "commentproducts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({EntityCreationListener.class})
public class CommentProduct extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public CommentProductEntityDTO toDto() {
        CommentProductEntityDTO dto = new CommentProductEntityDTO();
        dto.setId(this.id);
        dto.setComment(comment);
        dto.setCreatedBy(this.getCreatedBy());
        ProductEntityDTO idProduct = new ProductEntityDTO(product.getId());
        dto.setProducts(idProduct);
        return dto;
    }

    @Override
    public String toString() {
        return "CommentProduct{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", product=" + product +
                '}';
    }
}
