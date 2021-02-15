package com.example.hititcstask.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="RentACar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentACar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rentACarID;

    @Column(name="rent_a_car_name")
    private String rentACarName;

    @OneToMany(mappedBy="rentACar")
    private List<Car> carList;
}
