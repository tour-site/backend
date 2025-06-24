package com.project.tour.dto;

import com.project.tour.entity.Food;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FoodDTO {
    private Long id;
    private String name;
    private String city; 
    private String food_title;
    private String food_add;
    private String food_tell;
    private String food_time;
    private String food_menu;
    private String food_img;
    private String food_sum_img;
    private String food_detail;

    public FoodDTO(Food food) {
        this.id = food.getId();
        this.name = food.getName();
        this.city = food.getCity();
        this.food_title = food.getFood_title();
        this.food_add = food.getFood_add();
        this.food_tell = food.getFood_tell();
        this.food_time = food.getFood_time();
        this.food_menu = food.getFood_menu();
        this.food_img = food.getFood_img();
        this.food_sum_img = food.getFood_sum_img();
        this.food_detail = food.getFood_detail();
    }
}
