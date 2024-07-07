package bisma.project.nike.model;

import bisma.project.nike.dto.entity.CategoryEntityDTO;
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
import java.util.stream.Collectors;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners({EntityCreationListener.class})
public class Category extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    // nama mappedBy itu nama property @manytoone nya
    @JsonBackReference
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = bisma.project.nike.model.CategorySub.class)
    private List<CategorySub> subCategories;

    public CategoryEntityDTO toDto() {
//        List<CategoryEntityDTO.SubCategory> subCategoriesMapped= subCategories
//                .stream()
//                .map(sub -> new CategoryEntityDTO.SubCategory(sub.getId(), sub.getName(), sub.getDescription()))
//                .collect(Collectors.toList());

        return CategoryEntityDTO
                .builder()
                .id(this.getId())
                .name(this.getName())
                .description(this.getDescription())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
//                .subCategories(subCategoriesMapped)
                .build();
    }


    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", subCategories=" + subCategories +
                '}';
    }
}
