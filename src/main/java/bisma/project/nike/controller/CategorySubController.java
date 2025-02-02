package bisma.project.nike.controller;

import bisma.project.nike.dto.entity.CategorySubEntityDTO;
import bisma.project.nike.dto.request.CategorySubReqDTO;
import bisma.project.nike.dto.response.CommonResponse;
import bisma.project.nike.model.Category;
import bisma.project.nike.model.CategorySub;
import bisma.project.nike.repository.CategoryRepository;
import bisma.project.nike.repository.CategorySubRepository;
import bisma.project.nike.services.CategorySubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
@Validated
@RestController
@RequestMapping("/api/v1/sub-categories")
public class CategorySubController {

    @Autowired
    CategorySubRepository categorySubRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategorySubService categorySubService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAllSubCategory(@RequestParam(defaultValue = "5")int size,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(required = false) String name
                                                    ) {

        List<CategorySubEntityDTO>findAllSubCategories = categorySubService.findAllCategory(page, size, name);

        return CommonResponse.generateResponse(findAllSubCategories, "successfully get all datas", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Object> getSingleSubCategory(@PathVariable Long id) {
        CategorySubEntityDTO findById = categorySubService.findById(id);
        return CommonResponse.generateResponse(findById, "successfully get all datas", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{parentId}")
    public ResponseEntity<Object> insertSubCategory(@PathVariable Long parentId,
                                                    @RequestBody @Valid List<CategorySubReqDTO> categorySubReqDTO) {

        List<CategorySubEntityDTO> savedSubCategories = categorySubService.saveAllSubCategories(categorySubReqDTO, parentId);

        return CommonResponse.generateResponse(savedSubCategories, "successfully saved data", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Object> updateSubCategory(@PathVariable Long id,
                                                   @Validated(CategorySubReqDTO.UpdateValidationGroup.class) @RequestBody CategorySubReqDTO categorySubReqDTO) {

       CategorySubEntityDTO updatedCategorySub = categorySubService.updateById(id, categorySubReqDTO);

        return CommonResponse.generateResponse(updatedCategorySub, "successfully updated data", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Object> deleteSubCategoryById(@PathVariable Long id) {
        categorySubService.deleteById(id);
        return CommonResponse.generateResponse(true, "successfully delete data", HttpStatus.OK);
    }

}
