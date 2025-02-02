package bisma.project.nike.services;

import bisma.project.nike.auth.UserDetailsImpl;
import bisma.project.nike.controller.ProductController;
import bisma.project.nike.dto.request.CategorySubReqDTO;
import bisma.project.nike.dto.request.ProductCategoryDto;
import bisma.project.nike.dto.request.ProductsReqDTO;
import bisma.project.nike.dto.response.ProductResDTO;
import bisma.project.nike.model.Category;
import bisma.project.nike.model.CategorySub;
import bisma.project.nike.model.Product;
import bisma.project.nike.model.User;
import bisma.project.nike.repository.CategoryRepository;
import bisma.project.nike.repository.CategorySubRepository;
import bisma.project.nike.repository.ProductRepository;
import bisma.project.nike.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategorySubRepository categorySubRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public Map<String , Object> findAllProduct(String name,
                                                 Long categoryId,
                                                 String orderBy,
                                                 String typeOrder,
                                                 int page,
                                                 int size ) {

        Sort sort = Sort.by(Sort.Order.asc(orderBy));
        if (typeOrder.equals("desc")) {
            sort = Sort.by(Sort.Order.desc(orderBy));
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product>allProducts = productRepository.findAll(pageable);

        if (name != null && categoryId != null) {
            Set<CategorySub> allSubCategory = categorySubRepository.findAllById(List.of(categoryId));
            logger.debug("isinya apaan: {}", allSubCategory);
            allProducts = productRepository.findAllByNameLikeAndfindAllByCategory_Name("%" + name + "%",  allSubCategory, pageable);
        } else if (name != null) {
            allProducts = productRepository.findAllByNameLike("%" + name + "%", pageable);
        } else if (categoryId != null) {
            allProducts = productRepository.findAllByCategoriesProduct_Id(categoryId, pageable);
        }
        List<Product> products = allProducts.getContent();
        Map<String, Object> res = new HashMap<>();

        res.put("totalPages", allProducts.getTotalPages());
        res.put("pageNumber", allProducts.getPageable().getPageNumber());
        res.put("pageSize", allProducts.getPageable().getPageSize());
        res.put("isFirstPage", allProducts.isFirst());
        res.put("isLastPage", allProducts.isLast());
        res.put("products",products);
        return res;


    }

    @Transactional
    public ProductResDTO insertProduct(ProductsReqDTO productsReqDTO) {
        Product products = new Product();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        products.setCreatedBy(user.getUsername());
        products.setName(productsReqDTO.getName());
        products.setDescription(productsReqDTO.getDescription());
        products.setCover(productsReqDTO.getCover());
        products.setSummary(productsReqDTO.getSummary());
        Product createdProducts = productRepository.save(products);

        ProductResDTO productResDTO = new ProductResDTO();
        productResDTO.setId(createdProducts.getId());
        productResDTO.setCover(createdProducts.getCover());
        productResDTO.setCover(createdProducts.getCover());
        productResDTO.setName(createdProducts.getName());
        return productResDTO;
    }

    @Transactional
    public ProductResDTO updateProduct(ProductsReqDTO productsReqDTO,Long id) {
//        Product products = productRepository
//                .findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is not found"));
//
//        products.setName(productsReqDTO.getName());
//        products.setPrice(productsReqDTO.getPrice());
//        products.setDescription(productsReqDTO.getDescription());
//        products.setMainImg(productsReqDTO.getMainImg());
//
//        if (productsReqDTO.getCategoryId() != null) {
//            Category findCategory = categoryRepository
//                    .findById(productsReqDTO.getCategoryId())
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category is not found"));
//            products.setCategory(findCategory);
//        }
//
//        Product updatedProduct = productRepository.save(products);
//
//        return ProductResDTO
//                .builder()
//                .id(updatedProduct.getId())
//                .name(updatedProduct.getName())
//                .description(updatedProduct.getDescription())
//                .price(updatedProduct.getPrice())
//                .mainImg(updatedProduct.getMainImg())
//                .categoryId(updatedProduct.getCategory().getId())
//                .build();
        return null;
    }

    @Transactional
    public boolean deleteProductById(Long id) {
        try {
            Product findProduct = productRepository
                    .findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is not found"));

            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public Product findProductById(Long id) {
        Product findProductById = productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is not found"));

        return findProductById;
    }

    @Transactional
    public Product createProductCategories(Long id, CategorySubReqDTO categoriesDTOS) {
        Product findProduct = productRepository.findById(id).map(product -> {
            // cari dulu sub categorynya berdasarkan dto nya
            // kalau ada maka buat add ke product
            // kalau ga ada buat
            Long categoryId = categoriesDTOS.getId();
            System.out.println("category id " + categoryId);
            if (categoryId != null) {
              CategorySub findCategorySub =  categorySubRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
              product.addCategory(findCategorySub);
              productRepository.save(product);
            }
            return product;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is not found"));

        return null;
    }


}
