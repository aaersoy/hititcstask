package com.example.hititcstask.entity;


import lombok.*;

import javax.persistence.*;

@Entity(name = "Car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carID;

    @Column(name="plate_code")
    private String plateCode;

    @Column(name="rented")
    private boolean rented;

    @ManyToOne
    @JoinColumn(name="rentACarID")
    private RentACar rentACar;


}
