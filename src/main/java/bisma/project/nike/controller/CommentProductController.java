package bisma.project.nike.controller;

import bisma.project.nike.dto.entity.CommentProductEntityDTO;
import bisma.project.nike.dto.request.CommentProductReqDTO;
import bisma.project.nike.dto.response.CommentProductResDTO;
import bisma.project.nike.dto.response.CommonResponse;
import bisma.project.nike.model.CommentProduct;
import bisma.project.nike.model.Product;
import bisma.project.nike.repository.CommentProductRepository;
import bisma.project.nike.repository.ProductRepository;
import bisma.project.nike.services.CommentProductService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentProductController {


        @Autowired
        CommentProductRepository commentProductRepository;

        @Autowired
         ProductRepository productRepository;

        @Autowired
        CommentProductService commentProductService;

        @RequestMapping(method = RequestMethod.POST)
        public ResponseEntity<Object>insertComment(@Valid @RequestBody CommentProductReqDTO commentProductReqDTO) {
            CommentProductResDTO responseData = commentProductService.saveComment(commentProductReqDTO);


            return CommonResponse.generateResponse(responseData, "successfully save comment", HttpStatus.OK);
        }

        @RequestMapping(method = RequestMethod.GET)
        public ResponseEntity<Object>getAllComment(@RequestParam(required = false) Long productId,
                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                   @RequestParam(required = false, defaultValue = "10") int size
                                                   ) {
            Pageable pageable = PageRequest.of(page, size);
            Page<CommentProduct> findAllComment;
            if (productId != null) {
                findAllComment = commentProductRepository.findCommentProductByProductId(productId, pageable);
            } else {
                findAllComment = commentProductRepository.findAll(pageable);
            }
            List<CommentProductEntityDTO> allProduct = findAllComment.stream().map(CommentProduct::toDto).collect(Collectors.toList());


            return CommonResponse.generateResponse(allProduct, "successfully get all comment", HttpStatus.OK);
        }

        @RequestMapping(method = RequestMethod.PUT, path = "/{commentId}")
        public ResponseEntity<Object>updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentProductReqDTO commentProductReqDTO) {
            CommentProductResDTO resDTO = commentProductService.updateComment(commentProductReqDTO, commentId);

            return CommonResponse.generateResponse(resDTO, "successfully update product", HttpStatus.OK);
        }


        @RequestMapping(method = RequestMethod.DELETE)
        public ResponseEntity<Object>deleteComment(@RequestParam Long commentId) {
               boolean statusDel = commentProductService.deleteProduct(commentId);
               return CommonResponse.generateResponse(true, "successfully delete product", HttpStatus.OK);

        }

}
