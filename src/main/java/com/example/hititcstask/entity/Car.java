package com.example.hititcstask.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="Car")
@Data
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
