package bisma.project.nike.controller;

import bisma.project.nike.dto.request.ProductsReqDTO;
import bisma.project.nike.dto.response.CommonResponse;
import bisma.project.nike.dto.response.ProductResDTO;
import bisma.project.nike.model.Category;
import bisma.project.nike.model.Product;
import bisma.project.nike.model.User;
import bisma.project.nike.repository.CategoryRepository;
import bisma.project.nike.repository.ProductRepository;
import bisma.project.nike.repository.UserRepository;
import bisma.project.nike.services.auth.UserDetailsImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAllProducts(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) Long categoryId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "asc") String typeOrder,
                                                 @RequestParam(defaultValue = "id") String orderBy) {
        Sort sort = Sort.by(Sort.Order.asc(orderBy));
        if (typeOrder.equals("desc")) {
            sort = Sort.by(Sort.Order.desc(orderBy));
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> allProducts;

        if (name != null && categoryId != null) {
            allProducts = productRepository.findAllByNameLikeAndfindAllByCategory_Name("%" + name + "%",  categoryId, pageable);
        } else if (name != null) {
            allProducts = productRepository.findAllByNameLike("%" + name + "%", pageable);
        } else if (categoryId != null) {
            allProducts = productRepository.findAllByCategory_Name("%" + categoryId + "%", pageable);
        }  else {
            allProducts = productRepository.findAll(pageable);
        }


        return CommonResponse.generateResponse(allProducts, "successfully get all products", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object>insertProduct(@Valid @RequestBody ProductsReqDTO productsReqDTO) {
//        productRepository.save();
        Product products = new Product();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        products.setUser(user);

        Category category = categoryRepository
                .findById(productsReqDTO.getCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categories not found"));

        products.setCategory(category);
        products.setName(productsReqDTO.getName());
        products.setPrice(productsReqDTO.getPrice());
        products.setDescription(productsReqDTO.getDescription());
        Product createdProducts = productRepository.save(products);

        ProductResDTO resData = new ProductResDTO(createdProducts.getId(),
                createdProducts.getName(),
                createdProducts.getDescription(),
                createdProducts.getPrice(),
                createdProducts.getMainImg(),
                createdProducts.getCategory().getId());
        return CommonResponse.generateResponse(resData, "successfully create product", HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Object>updateProduct(@PathVariable Long id,@Valid @RequestBody ProductsReqDTO productsReqDTO) {
        Product products = productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is not found"));

        products.setName(productsReqDTO.getName());
        products.setPrice(productsReqDTO.getPrice());
        products.setDescription(productsReqDTO.getDescription());
        products.setMainImg(productsReqDTO.getMainImg());

        if (productsReqDTO.getCategoryId() != null) {
            Category findCategory = categoryRepository
                    .findById(productsReqDTO.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category is not found"));
            products.setCategory(findCategory);
        }

        Product updatedProduct = productRepository.save(products);
        ProductResDTO res = new ProductResDTO(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getDescription(), updatedProduct.getPrice(), updatedProduct.getMainImg(), updatedProduct.getCategory().getId());
        return CommonResponse.generateResponse(res ,"successfully update product", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Object>deleteProduct(@PathVariable Long id) {
        Product findProduct = productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is not found"));

        productRepository.deleteById(id);

        return CommonResponse.generateResponse(true, "successfully delete product", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Object>getProductById(@PathVariable Long id) {
        Product findProductById = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is not found"));
        ProductResDTO res = new ProductResDTO(
                findProductById.getId(),
                findProductById.getName(),
                findProductById.getDescription(),
                findProductById.getPrice(),
                findProductById.getMainImg(),
                findProductById.getCategory().getId());
        return CommonResponse.generateResponse(res, "successfully get detail product", HttpStatus.OK);
    }


}
