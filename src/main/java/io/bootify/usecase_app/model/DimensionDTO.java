package io.bootify.usecase_app.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DimensionDTO {

    private Long dimensionId;

    @Size(max = 255)
    private String dimensionName;


}
