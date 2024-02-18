package bisma.project.nike.repository;

import bisma.project.nike.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);

    Page<Category> findAll(Pageable pageable);

    Page<Category>findAllById(Long id, Pageable pageable);


}
