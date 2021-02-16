package com.example.hititcstask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestNewCar {

    @NotNull
    private String plateCode;
    @NotNull
    private String rentACarID;
}
