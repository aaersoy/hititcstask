package com.example.hititcstask.model;

import lombok.*;



@Getter
@NoArgsConstructor
public class RequestUpdateCar {
    @NonNull
    private Long carID;
    @NonNull
    private String plateCode;
    @NonNull
    private Long rentACarID;
}
