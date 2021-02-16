package com.example.hititcstask.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class ResponseNewCar {

    private Long carID;
    private String plateCode;
    private Long rentACarID;
    private boolean rented;
}
