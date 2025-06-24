// 📁 src/main/java/com/project/tour/entity/Member.java
package com.project.tour.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member") // 실제 DB 테이블 이름
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // bcrypt 인코딩된 비밀번호

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true)
    private String gender;

    @Column(nullable = true)
    private String birthday; // ex: 0101

    @Column(nullable = true, unique = true)
    private String phoneNumber;
}
