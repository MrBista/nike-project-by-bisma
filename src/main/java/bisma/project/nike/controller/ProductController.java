package bisma.project.nike.controller;

import bisma.project.nike.dto.request.ProductsReqDTO;
import bisma.project.nike.dto.response.CommonResponse;
import bisma.project.nike.dto.response.ProductResDTO;
import bisma.project.nike.model.Product;
import bisma.project.nike.repository.CategoryRepository;
import bisma.project.nike.repository.ProductRepository;
import bisma.project.nike.repository.UserRepository;
import bisma.project.nike.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAllProducts(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) Long categoryId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "asc", required = false) String typeOrder,
                                                 @RequestParam(defaultValue = "id", required = false) String orderBy) {


       Page<Product> allProducts = productService.findAllProduct(name, categoryId, orderBy, typeOrder, page, size);

        return CommonResponse.generateResponse(allProducts, "successfully get all products", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object>insertProduct(@Valid @RequestBody ProductsReqDTO productsReqDTO) {

       ProductResDTO resData = productService.insertProduct(productsReqDTO);

        return CommonResponse.generateResponse(resData, "successfully create product", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Object>updateProduct(@PathVariable Long id,@Valid @RequestBody ProductsReqDTO productsReqDTO) {

        ProductResDTO res = productService.updateProduct(productsReqDTO, id);

        return CommonResponse.generateResponse(res ,"successfully update product", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Object>deleteProduct(@PathVariable Long id) {

        productService.deleteProductById(id);

        return CommonResponse.generateResponse(true, "successfully delete product", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Object>getProductById(@PathVariable Long id) {
        ProductResDTO res = productService.findProductById(id);
        return CommonResponse.generateResponse(res, "successfully get detail product", HttpStatus.OK);
    }


}
