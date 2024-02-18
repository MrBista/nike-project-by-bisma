package bisma.project.nike.services;

import bisma.project.nike.dto.request.CategoryReqDTO;
import bisma.project.nike.dto.response.CategoryResDTO;
import bisma.project.nike.model.Category;
import bisma.project.nike.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<CategoryResDTO> findAllCategory(String orderBy, String typeOrder, int page, int size, Long id) {
        Sort sort = Sort.by(Sort.Order.asc(orderBy));
        if (typeOrder.equals("desc")) {
            sort = Sort.by(Sort.Order.desc(orderBy));
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> findAllCategories;
        if (id != null && id > 0) {
            findAllCategories = categoryRepository.findAllById(id, pageable);
        } else {
            findAllCategories = categoryRepository.findAll(pageable);
        }


        return findAllCategories
                .stream()
                .map(category -> new CategoryResDTO(category.getId(),category.getName()))
                .collect(Collectors.toList());
    }

    public CategoryResDTO findCategoryById(Long id) {
        Category findCategory = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "categories not found"));
        return CategoryResDTO
                .builder()
                .id(findCategory.getId())
                .categoryName(findCategory.getName())
                .build();
    }

    @Transactional
    public CategoryResDTO saveCategory(CategoryReqDTO categoryReqDTO) {
        Category category = new Category();
        category.setName(categoryReqDTO.getNameCategory());

        Category createdCategory = categoryRepository.save(category);
        CategoryResDTO categoryResDTO = new CategoryResDTO(createdCategory.getId(), createdCategory.getName());
        return CategoryResDTO
                .builder()
                .id(createdCategory.getId())
                .categoryName(category.getName())
                .build();
    }

    @Transactional
    public CategoryResDTO updateCategory(CategoryReqDTO categoryReqDTO, Long id) {
        Category findCategory = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category is not found"));
        findCategory.setName(categoryReqDTO.getNameCategory());

        categoryRepository.save(findCategory);

        return CategoryResDTO
                .builder()
                .id(findCategory.getId())
                .categoryName(findCategory.getName())
                .build();
    }
    @Transactional
    public boolean deleteCategory(Long id) {
        Category findCategory = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category is not found"));


        categoryRepository.delete(findCategory);
        return true;
    }
}
