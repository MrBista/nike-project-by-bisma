package bisma.project.nike.model;

import bisma.project.nike.dto.entity.CategorySubEntityDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sub_categories")
@EntityListeners({EntityCreationListener.class})
public class CategorySub extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = bisma.project.nike.model.Category.class)
    @JoinColumn(name = "parent_id",referencedColumnName = "id")
    @JsonIgnore
    private Category category;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "categoriesProduct", fetch = FetchType.LAZY, targetEntity = bisma.project.nike.model.Product.class)
    @JsonIgnore
    private Set<Product> products = new HashSet<>();



//    @JsonManagedReference
//    @OneToMany(mappedBy = "categorysub", fetch = FetchType.LAZY)
//    @Column(name = "category_id")
//    private List<Product> products;
    public CategorySubEntityDTO toDTO() {
        CategorySubEntityDTO.Category category1 = new CategorySubEntityDTO.Category(this.category.getId(), this.category.getName());

        return CategorySubEntityDTO.builder()
                .category(category1)
                .createdAt(this.getCreatedAt())
                .description(this.getDescription())
                .id(this.getId())
                .name(this.getName())
                .build();
    }


    @Override
    public String toString() {
        return "CategorySub{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
