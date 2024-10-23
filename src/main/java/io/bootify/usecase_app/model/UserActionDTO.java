package io.bootify.usecase_app.model;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UserActionDTO {

    private Long actionId;
    private String actionName;
    private LocalDate actionDate;
    private Long userId;
}