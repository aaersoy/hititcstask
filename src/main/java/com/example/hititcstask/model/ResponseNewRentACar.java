package com.example.hititcstask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseNewRentACar {
    private Long rentACarID;
    private String rentACarName;
    private Long carCount;
}
