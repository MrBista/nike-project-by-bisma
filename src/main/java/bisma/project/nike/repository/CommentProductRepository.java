package bisma.project.nike.repository;

import bisma.project.nike.model.CommentProduct;
import bisma.project.nike.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface  CommentProductRepository extends JpaRepository<CommentProduct, Long> {

    @Modifying
    @Transactional
    CommentProduct save(CommentProduct commentProduct);

    Page<CommentProduct> findCommentProductByProductId(Long productId,Pageable pageable);

    Page<CommentProduct>findAll(Pageable pageable);
}
