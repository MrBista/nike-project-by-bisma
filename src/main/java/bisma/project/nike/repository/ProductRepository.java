package bisma.project.nike.repository;

import bisma.project.nike.model.CategorySub;
import bisma.project.nike.model.Product;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    Product save(@NonNull Product products);

    @Transactional
    void deleteById(Long id);

    Optional<Product> findById(Long id);


    @Override
    Page<Product> findAll(Pageable pageable);
    // Metode untuk mendapatkan data dalam bentuk DTO tanpa @Query

    Page<Product> findAllByNameLike(String name, Pageable pageable);

    Page<Product> findAllByCategoriesProduct_Id(Long id, Pageable pageable);
//    Page<Product> findAllByCategorySubsId(Long id, Pageable pageable);

    @Query(value = "select p from Product as p where p.name like :name and p.categoriesProduct like :categorySubs")
    Page<Product>findAllByNameLikeAndfindAllByCategory_Name(@Param("name") String name, @Param("categorySubs") Set<CategorySub> categorySubs, Pageable pageable);


    @Query(value = "select count(p) from Product as p where p.createdAt between :startDate and :endDate")
    long countByDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    long countByCreatedAtBetween(long fromDate, long toDate);


//    @Query(value = "select p from Product as p where p.name like :name and p.categorySubsId like :categoryId")
//    Page<Product>findAllByNameLikeAndfindAllByCategory_Name(@Param("name")String name,@Param("categoryId") Long categoryId, Pageable pageable);
}
