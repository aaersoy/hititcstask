package com.example.hititcstask.model;

import lombok.*;


@Getter
@NoArgsConstructor
public class RequestNewCar {

    @NonNull
    private String plateCode;
    @NonNull
    private String rentACarID;
}
