package com.project.tour.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "people")
public class People {

    @Id
    @SequenceGenerator(
        name = "people_seq_gen",
        sequenceName = "people_seq", // 오라클에 만든 시퀀스 이름
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "people_seq_gen"
    )
    @Column(name = "people_id")
    private Long peopleId;

    private String tour;
    private String city;
    private int total;
    private int year;
    private int month;

    public String getTour() {
        return this.tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    // 기본 생성자
    public People() {}
}
