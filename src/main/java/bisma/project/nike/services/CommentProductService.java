package bisma.project.nike.services;

import bisma.project.nike.dto.request.CommentProductReqDTO;
import bisma.project.nike.dto.response.CommentProductResDTO;
import bisma.project.nike.model.CommentProduct;
import bisma.project.nike.model.Product;
import bisma.project.nike.repository.CommentProductRepository;
import bisma.project.nike.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommentProductService {
    @Autowired
    CommentProductRepository commentProductRepository;

    @Autowired
    ProductRepository productRepository;

    public CommentProductResDTO saveComment(CommentProductReqDTO commentProductReqDTO) {
        CommentProduct commentProduct = new CommentProduct();
        commentProduct.setComment(commentProductReqDTO.getComment());

        if (commentProductReqDTO.getProductId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "product id harus diisi");
        }

        // cari product
        Product product = productRepository
                .findById(commentProductReqDTO.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product tidak ditemukan"));

        commentProduct.setProduct(product);

        CommentProduct result = commentProductRepository.save(commentProduct);
        if (result == null) {;
            throw new RuntimeException("gagal menyimpan result");
        }

        return CommentProductResDTO
                .builder()
                .comment(result.getComment())
                .productId(result.getId())
                .build();
    }

    @Transactional
    public CommentProductResDTO updateComment(CommentProductReqDTO commentProductReqDTO, Long commentId) {


        CommentProduct findComment = commentProductRepository
                .findById(commentId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "comment not found"));


        findComment.setComment(commentProductReqDTO.getComment());

        CommentProduct commentProduct = commentProductRepository.save(findComment);

        if (commentProduct == null) {
            throw new RuntimeException("failed update comment");
        }


        return   CommentProductResDTO
                .builder()
                .productId(findComment.getId())
                .comment(findComment.getComment())
                .build();
    }

    @Transactional
    public boolean deleteProduct(Long commentId) {
        CommentProduct commentProduct = commentProductRepository
                .findById(commentId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "comment not found"));

        commentProductRepository.delete(commentProduct);
        return true;
    }
}
