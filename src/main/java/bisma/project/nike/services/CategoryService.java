package bisma.project.nike.services;

import bisma.project.nike.dto.entity.CategoryEntityDTO;
import bisma.project.nike.dto.request.CategoryReqDTO;
import bisma.project.nike.dto.response.CategoryResDTO;
import bisma.project.nike.model.Category;
import bisma.project.nike.model.CategorySub;
import bisma.project.nike.repository.CategoryRepository;
import bisma.project.nike.repository.CategorySubRepository;
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

    @Autowired
    CategorySubRepository categorySubRepository;

    public List<CategoryEntityDTO> findAllCategory(String orderBy, String typeOrder, int page, int size, Long id) {
        Sort sort = Sort.by(Sort.Order.desc(orderBy));
//        if (typeOrder.equals("asc")) {
//            sort = Sort.by(Sort.Order.desc(orderBy));
//        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> findAllCategories;
        if (id != null && id > 0) {
            findAllCategories = categoryRepository.findAllById(id, pageable);
        } else {
            findAllCategories = categoryRepository.findAll(pageable);
        }


        return findAllCategories
                .stream()
                .map(Category::toDto)
                .collect(Collectors.toList());
    }

    public CategoryEntityDTO findCategoryById(Long id) {

        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category is not found"))
                .toDto();
    }

    @Transactional
    public CategoryEntityDTO saveCategory(CategoryReqDTO categoryReqDTO) {
        Category category = new Category();
        category.setName(categoryReqDTO.getName());
        category.setDescription(categoryReqDTO.getDescription());
//        List<CategorySub> subCategories= categoryReqDTO
//                .getSubCategories()
//                .stream()
//                .map(subCategory -> {
//                    CategorySub sub = new CategorySub();
//                    sub.setName(subCategory.getName());
//                    sub.setDescription(subCategory.getDescription());
//                    sub.setCategory(category);
//                    return sub;
//                }).collect(Collectors.toList());
//        category.setSubCategories(subCategories);

        return categoryRepository.save(category).toDto();
    }

    @Transactional
    public CategoryEntityDTO updateCategory(CategoryReqDTO categoryReqDTO, Long id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category is not found"));

        category.setName(categoryReqDTO.getName());
        category.setDescription(category.getDescription());

        categorySubRepository.deleteAllByParentId(category);
//        List<CategorySub> subCategories= categoryReqDTO
//                .getSubCategories()
//                .stream()
//                .map(subCategory -> {
//                    CategorySub sub = new CategorySub();
//                    sub.setName(subCategory.getName());
//                    sub.setDescription(subCategory.getDescription());
//                    sub.setCategory(category);
//                    return sub;
//                }).collect(Collectors.toList());

//        category.setSubCategories(subCategories);

        return categoryRepository.save(category).toDto();
    }

    @Transactional
    public boolean deleteCategory(Long id) {
        categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category is not found"));

        categoryRepository.deleteById(id);
        return true;
    }
}
