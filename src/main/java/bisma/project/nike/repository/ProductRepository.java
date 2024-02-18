package bisma.project.nike.repository;

import bisma.project.nike.model.Product;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    Product save(@NonNull Product products);

    @Transactional
    void deleteById(Long id);


    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByNameLike(String name, Pageable pageable);
    Page<Product> findAllByCategory_Name(String name, Pageable pageable);

    @Query(value = "select p from Product as p where p.name like :name and p.category.id like :categoryId")
    Page<Product>findAllByNameLikeAndfindAllByCategory_Name(@Param("name")String name,@Param("categoryId") Long categoryId, Pageable pageable);
}
