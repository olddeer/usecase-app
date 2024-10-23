package io.bootify.usecase_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {

    private Long commentId;

    @NotNull
    @Size(max = 255)
    private String comment;

    private Long useCaseId;


}
