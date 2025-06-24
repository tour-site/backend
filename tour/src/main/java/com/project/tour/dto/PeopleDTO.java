package com.project.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PeopleDTO {
    private String city;
    private long tourCount;
    private long totalVisitors;

}
