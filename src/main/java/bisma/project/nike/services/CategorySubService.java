package bisma.project.nike.services;

import bisma.project.nike.dto.entity.CategorySubEntityDTO;
import bisma.project.nike.dto.request.CategorySubReqDTO;
import bisma.project.nike.model.Category;
import bisma.project.nike.model.CategorySub;
import bisma.project.nike.repository.CategoryRepository;
import bisma.project.nike.repository.CategorySubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorySubService {

    @Autowired
    CategorySubRepository categorySubRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<CategorySubEntityDTO> findAllCategory(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        List<CategorySubEntityDTO> findAllSubCategories = new ArrayList<>();
        if (!name.isEmpty()) {
            findAllSubCategories = categorySubRepository.findByName(name).stream().map(CategorySub::toDTO).collect(Collectors.toList());
        } else {
            findAllSubCategories = categorySubRepository.findAll(pageable).stream().map(CategorySub::toDTO).collect(Collectors.toList());
        }

        return findAllSubCategories;
    }

    public CategorySubEntityDTO findById(Long id) {
        CategorySubEntityDTO findById = categorySubRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "sub categories not found"))
                .toDTO();
        return findById;
    }

    public List<CategorySubEntityDTO> saveAllSubCategories(List<CategorySubReqDTO> categorySubReqDTO, Long parentId) {
        Category findCategory = categoryRepository
                .findById(parentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));

        List<CategorySub> categorySubs = categorySubReqDTO
                .stream().map(cat -> {
            CategorySub catSub = new CategorySub();
            catSub.setCategory(findCategory);
            catSub.setName(cat.getName());
            catSub.setDescription(cat.getDescription());
            return catSub;
        })
                .collect(Collectors.toList());

        return categorySubRepository.saveAll(categorySubs).stream().map(CategorySub::toDTO).collect(Collectors.toList());
    }

    public CategorySubEntityDTO updateById(Long id, CategorySubReqDTO categorySubReqDTO) {
        CategorySub categorySub = categorySubRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "sub category not found"));

        categorySub.setName(categorySubReqDTO.getName());
        categorySub.setDescription(categorySubReqDTO.getDescription());

        return categorySubRepository
                .save(categorySub)
                .toDTO();
    }

    public  void deleteById(Long id) {
        CategorySub findCategory = categorySubRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "sub category not found"));

        categorySubRepository.delete(findCategory);
    }
}
