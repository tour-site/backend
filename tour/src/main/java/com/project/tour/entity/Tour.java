package com.project.tour.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tour_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tour {

    @Id
    @Column(name = "tour_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tour_name")
    private String name;

    @Column(name = "tour_city")
    private String city;

    private String tour_title;
    private String tour_side;
    private String tour_add;
    private String tour_tell;
    private String tour_site;
    private String tour_rest;
    private String tour_time;
    private String tour_cost;
    private String tour_img;
    private String tour_sum_img;
    private String tour_detail;
}
