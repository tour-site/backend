package com.project.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.project.tour.dto.PeopleDTO;
import com.project.tour.entity.People;

public interface PeopleRepository extends Repository<People, Long> {

    @Query("SELECT new com.project.tour.dto.PeopleDTO(p.city, COUNT(DISTINCT p.tour), SUM(p.total)) " +
           "FROM People p WHERE p.year = :year AND p.month = :month GROUP BY p.city")
    List<PeopleDTO> getPeopleStatsByYearMonth(int year, int month);
}
