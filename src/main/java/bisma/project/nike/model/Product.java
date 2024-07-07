package bisma.project.nike.model;

import bisma.project.nike.dto.entity.CategoryEntityDTO;
import bisma.project.nike.dto.entity.ProductEntityDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Column(name = "summary")
    private String summary;


    @Column(name = "cover")
    private String cover;

    @Column(name = "created_by")
    private String createdBy;


    @ManyToMany(fetch = FetchType.LAZY, targetEntity = bisma.project.nike.model.CategorySub.class)
    @JoinTable( name = "products_sub_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_category_id")
    )
    @JsonIgnore
    private Set<CategorySub> categoriesProduct = new HashSet<>();


    public void addCategory (CategorySub categorySub) {
        this.categoriesProduct.add(categorySub);
        categorySub.getProducts().add(this);
    }

    public void removeCategory(long categoryId) {
        CategorySub categorySub = this.categoriesProduct.stream().filter(t -> t.getId() == categoryId).findFirst().orElse(null);
        if (categorySub != null) {
            this.categoriesProduct.remove(categorySub);
            categorySub.getProducts().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", summary='" + summary + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
