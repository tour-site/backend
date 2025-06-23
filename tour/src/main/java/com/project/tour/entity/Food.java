package com.project.tour.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @Column(name = "food_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_name")
    private String name;

    @Column(name = "tour_city")
    private String city; 
}

