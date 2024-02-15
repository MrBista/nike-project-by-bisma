package bisma.project.nike.controller;

import bisma.project.nike.dto.request.CategoryReqDTO;
import bisma.project.nike.dto.response.CategoryResDTO;
import bisma.project.nike.dto.response.CommonResponse;
import bisma.project.nike.model.Category;
import bisma.project.nike.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object>getAllCategory() {
        List<Category> findAllCategories = categoryRepository.findAll();

        List<CategoryResDTO> allCategories = findAllCategories
                .stream()
                .map(category -> new CategoryResDTO(category.getId(),category.getName()))
                .collect(Collectors.toList());

        return CommonResponse.generateResponse(allCategories, "successfully get all categories", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Object>getCategoryById(@PathVariable Long id) {
        Category findCategory = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "categories not found"));
        CategoryResDTO categoryResDTO = new CategoryResDTO(findCategory.getId(), findCategory.getName());
        return CommonResponse.generateResponse(categoryResDTO, "successfully get single categories", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> insertCategory(@Valid @RequestBody CategoryReqDTO categoryReqDTO) {
        Category category = new Category();
        category.setName(categoryReqDTO.getNameCategory());

        Category createdCategory = categoryRepository.save(category);
        CategoryResDTO categoryResDTO = new CategoryResDTO(createdCategory.getId(), createdCategory.getName());
        return CommonResponse.generateResponse(categoryResDTO, "successfully insert category", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Object> updateCategory(@Valid @RequestBody CategoryReqDTO categoryReqDTO, @PathVariable Long id) {
        Category findCategory = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category is not found"));
        findCategory.setName(categoryReqDTO.getNameCategory());

        categoryRepository.save(findCategory);
        CategoryResDTO categoryResDTO = new CategoryResDTO(findCategory.getId(), findCategory.getName());
        return CommonResponse.generateResponse(categoryResDTO, "successfully update category", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Object> deleteCategory(@Valid @RequestBody CategoryReqDTO categoryReqDTO, @PathVariable Long id) {
        Category findCategory = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category is not found"));


        categoryRepository.delete(findCategory);
        return CommonResponse.generateResponse(true, "successfully update category", HttpStatus.OK);
    }



}
