// 📁 src/main/java/com/project/tour/entity/Member.java
package com.project.tour.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long member_id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = true)
    private String nickname;

    @Column(name = "profile_image", nullable = true)
    private String profileImage;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "birthday", nullable = true)
    private String birthday;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
}
