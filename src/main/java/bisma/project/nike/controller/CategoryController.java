package bisma.project.nike.controller;

import bisma.project.nike.dto.request.CategoryReqDTO;
import bisma.project.nike.dto.response.CategoryResDTO;
import bisma.project.nike.dto.response.CommonResponse;
import bisma.project.nike.model.Category;
import bisma.project.nike.repository.CategoryRepository;
import bisma.project.nike.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    CategoryService categoryService;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object>getAllCategory(@RequestParam Long id,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "asc") String typeOrder,
                                                @RequestParam(defaultValue = "id") String orderBy ) {

        List<CategoryResDTO> allCategories = categoryService.findAllCategory(orderBy,typeOrder,page,size,id);

        return CommonResponse.generateResponse(allCategories, "successfully get all categories", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Object>getCategoryById(@PathVariable Long id) {
        CategoryResDTO categoryResDTO = categoryService.findCategoryById(id);
        return CommonResponse.generateResponse(categoryResDTO, "successfully get single categories", HttpStatus.OK);
    }

    

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> insertCategory(@Valid @RequestBody CategoryReqDTO categoryReqDTO) {
        CategoryResDTO categoryResDTO = categoryService.saveCategory(categoryReqDTO);
        return CommonResponse.generateResponse(categoryResDTO, "successfully insert category", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Object> updateCategory(@Valid @RequestBody CategoryReqDTO categoryReqDTO, @PathVariable Long id) {
        CategoryResDTO categoryResDTO = categoryService.updateCategory(categoryReqDTO, id);

        return CommonResponse.generateResponse(categoryResDTO, "successfully update category", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
       boolean statusDel = categoryService.deleteCategory(id);
        return CommonResponse.generateResponse(true, "successfully update category", HttpStatus.OK);
    }



}
