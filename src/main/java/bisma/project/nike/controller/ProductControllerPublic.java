package bisma.project.nike.controller;

import bisma.project.nike.dto.response.CommonResponse;
import bisma.project.nike.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/pub/products")
public class ProductControllerPublic {

    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAllProducts(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) Long categoryId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "asc", required = false) String typeOrder,
                                                 @RequestParam(defaultValue = "id", required = false) String orderBy) {


        Map<String, Object> allProducts = productService.findAllProduct(name, categoryId, orderBy, typeOrder, page, size);

        return CommonResponse.generateResponse(allProducts, "successfully get all products", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Object> getSingleProduct(@PathVariable Long id) {

        return null;
    }
}
