package bisma.project.nike.controller;
import bisma.project.nike.dto.entity.CategoryEntityDTO;
import bisma.project.nike.dto.request.CategoryReqDTO;
import bisma.project.nike.dto.response.CommonResponse;
import bisma.project.nike.repository.CategoryRepository;
import bisma.project.nike.repository.CategorySubRepository;
import bisma.project.nike.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2. Category", description = "This endpoint is used to Category purposes")
@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "http://localhost:8000", maxAge = 3600, allowCredentials="true")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategorySubRepository categorySubRepository;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object>getAllCategory(@RequestParam(required = false) Long id,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "desc") String typeOrder,
                                                @RequestParam(defaultValue = "id") String orderBy ) {

        List<CategoryEntityDTO> allCategories = categoryService
                                                    .findAllCategory(orderBy,typeOrder, page, size, id);

        return CommonResponse.generateResponse(allCategories, "successfully get all categories", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Object>getCategoryById(@PathVariable Long id) {

        CategoryEntityDTO categoryResDTO = categoryService.findCategoryById(id);

        return CommonResponse.generateResponse(categoryResDTO, "successfully get single categories", HttpStatus.OK);
    }

    

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> insertCategory(@Valid @RequestBody CategoryReqDTO categoryReqDTO) {

        CategoryEntityDTO categoryResDTO = categoryService.saveCategory(categoryReqDTO);

        return CommonResponse.generateResponse(categoryResDTO, "successfully insert category", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Object> updateCategory(@Valid @RequestBody CategoryReqDTO categoryReqDTO, @PathVariable Long id) {
        CategoryEntityDTO categoryResDTO = categoryService.updateCategory(categoryReqDTO, id);

        return CommonResponse.generateResponse(categoryResDTO, "successfully update category", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);

        return CommonResponse.generateResponse(true, "successfully update category", HttpStatus.OK);
    }



}
