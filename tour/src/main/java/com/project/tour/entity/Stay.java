package com.project.tour.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stay_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stay {

    @Id
    @Column(name = "stay_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stay_name")
    private String name;

    @Column(name = "tour_city")
    private String city;
}

