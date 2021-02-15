package com.example.hititcstask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateCar {
    @NotNull
    private Long carID;
    @NotNull
    private String plateCode;
    @NotNull
    private Long rentACarID;
}
