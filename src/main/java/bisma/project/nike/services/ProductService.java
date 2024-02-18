package bisma.project.nike.services;

import bisma.project.nike.dto.request.ProductsReqDTO;
import bisma.project.nike.dto.response.ProductResDTO;
import bisma.project.nike.model.Category;
import bisma.project.nike.model.Product;
import bisma.project.nike.model.User;
import bisma.project.nike.repository.CategoryRepository;
import bisma.project.nike.repository.ProductRepository;
import bisma.project.nike.repository.UserRepository;
import bisma.project.nike.auth.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public Page<Product> findAllProduct(String name,
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
        return allProducts;
    }

    @Transactional
    public ProductResDTO insertProduct(ProductsReqDTO productsReqDTO) {
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
        products.setStatus(productsReqDTO.getStatus());
        Product createdProducts = productRepository.save(products);


        return new ProductResDTO(createdProducts.getId(),
                createdProducts.getName(),
                createdProducts.getDescription(),
                createdProducts.getPrice(),
                createdProducts.getMainImg(),
                createdProducts.getCategory().getId());
    }

    @Transactional
    public ProductResDTO updateProduct(ProductsReqDTO productsReqDTO,Long id) {
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

        return ProductResDTO
                .builder()
                .id(updatedProduct.getId())
                .name(updatedProduct.getName())
                .description(updatedProduct.getDescription())
                .price(updatedProduct.getPrice())
                .mainImg(updatedProduct.getMainImg())
                .categoryId(updatedProduct.getCategory().getId())
                .build();
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

    public ProductResDTO findProductById(Long id) {
        Product findProductById = productRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is not found"));

        return ProductResDTO
                .builder()
                .id(findProductById.getId())
                .name(findProductById.getName())
                .description(findProductById.getDescription())
                .price(findProductById.getPrice())
                .mainImg(findProductById.getMainImg())
                .categoryId(findProductById.getCategory().getId())
                .build();
    }

}
