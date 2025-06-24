package com.project.tour.dto;

import com.project.tour.entity.Stay;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StayDTO {
    private Long id;
    private String name;
    private String city;
    private String stay_add;
    private String stay_tell;
    private String stay_site;
    private String stay_park;
    private String stay_img;

    public StayDTO(Stay stay) {
        this.id = stay.getId();
        this.name = stay.getName();
        this.city = stay.getCity();
        this.stay_add = stay.getStay_add();
        this.stay_tell = stay.getStay_tell();
        this.stay_site = stay.getStay_site();
        this.stay_park = stay.getStay_park();
        this.stay_img = stay.getStay_img();
    }
}
