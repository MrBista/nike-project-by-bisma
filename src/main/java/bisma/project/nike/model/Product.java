package bisma.project.nike.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(EntityCreationListener.class)
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "main_img")
    private String mainImg;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    // 0 in active, 1 active
    @Column(name = "status", columnDefinition = "VARCHAR(255) DEFAULT 1")
    private Long status;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @Column(name = "product_id")
    private List<CommentProduct> comment;

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", mainImg='" + mainImg + '\'' +
                ", category=" + category +
                '}';
    }
}
