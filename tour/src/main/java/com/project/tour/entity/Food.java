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

    private String food_title;
    private String food_add;
    private String food_tell;
    private String food_time;
    private String food_menu;
    private String food_img;
    private String food_sum_img;
    private String food_detail;
}

