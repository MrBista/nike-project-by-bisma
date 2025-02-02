package bisma.project.nike.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CategorySubReqDTO {
    private Long id;
    @NotBlank(message = "name sub categories cannot be blank")
    @NotNull(message = "name sub categories cannot be null", groups = {UpdateValidationGroup.class})
    private String name;
    @NotBlank(message = "description cannot be blank")
    private String description;

    public interface UpdateValidationGroup extends Default {
    }



}
