package io.bootify.usecase_app.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MeasureDTO {

    private Long measureId;

    @Size(max = 255)
    private String measureName;

}
