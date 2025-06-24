package com.project.tour.dto;

import com.project.tour.entity.Tour;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TourDTO {
    private Long id;
    private String name;
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

    public TourDTO(Tour tour) {
        this.id = tour.getId();
        this.name = tour.getName();
        this.city = tour.getCity();
        this.tour_title = tour.getTour_title();
        this.tour_side = tour.getTour_side();
        this.tour_add = tour.getTour_add();
        this.tour_tell = tour.getTour_tell();
        this.tour_site = tour.getTour_site();
        this.tour_rest = tour.getTour_rest();
        this.tour_time = tour.getTour_time();
        this.tour_cost = tour.getTour_cost();
        this.tour_img = tour.getTour_img();
        this.tour_sum_img = tour.getTour_sum_img();
        this.tour_detail = tour.getTour_detail();
    }
}
