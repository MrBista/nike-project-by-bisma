package bisma.project.nike.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentProductReqDTO {
    @NotNull(message = "comment harus diisi")
    @NotBlank(message = "comment tidak boleh kosong")
    private String comment;

//    @NotNull(message = "product id harus diisi")
//    @NotBlank(message = "product id tidak boleh kosong")
    private Long productId;

    @Override
    public String toString() {
        return "CommentProductReqDTO{" +
                "comment='" + comment + '\'' +
                ", productId=" + productId +
                '}';
    }
}
