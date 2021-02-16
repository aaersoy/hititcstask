package com.example.hititcstask.model;

import lombok.*;



@Getter
@NoArgsConstructor
public class RequestUpdateRentACar {
    @NonNull
    private Long rentACarID;
    @NonNull
    private String rentACarName;
}
