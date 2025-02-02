package bisma.project.nike.repository;
import bisma.project.nike.controller.ProductController;
import bisma.project.nike.dto.request.ProductCategoryDto;
import bisma.project.nike.model.Category;
import bisma.project.nike.model.CategorySub;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategorySubRepository extends JpaRepository<CategorySub, Long> {
    @Modifying
    @Query("DELETE FROM CategorySub as s WHERE s.category = :parentId")
    @Transactional
    void deleteAllByParentId(@Param("parentId") Category parentId);

    List<CategorySub> findByName(String name);
    @Query("SELECT s FROM CategorySub s WHERE s.id IN (:categoryIds)")
    Set<CategorySub> findAllById(@Param("categoryIds") List<Long> categoryIds);

}
