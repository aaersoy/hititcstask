package com.example.hititcstask.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseNewCar {

    private Long carID;
    private String plateCode;
    private Long rentACarID;
    private boolean rented;
}
